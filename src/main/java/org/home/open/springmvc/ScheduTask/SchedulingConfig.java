package org.home.open.springmvc.ScheduTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.home.open.dao.model.HistoryMTInfo;
import org.home.open.springmvc.mysqlController.HistoryMapper;
import org.home.open.springmvc.ssdbConfig.SsdbModel;
import org.home.open.springmvc.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.alibaba.fastjson.JSON;
import com.hyd.ssdb.SsdbClient;
import com.hyd.ssdb.util.KeyValue;

/***********
 * 删除未知数据，重新载入新的未知数据
 * @author Administrator
 *
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {
	@Autowired 
	SsdbModel ssdbModel;
	@Autowired
	private SsdbClient ssdb1;
	@Autowired
	private HistoryMapper historyMapper;
    @Scheduled(fixedRate=900000) // 每15分钟执行一次
    public void getToken() {
    	//规定时间段内拉取数据
    	int currentHour =  Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    	
    	if (DateUtils.comparedDataTime(currentHour,ssdbModel.getBegintime(),ssdbModel.getEndtime())) {
    		long beginTime = System.currentTimeMillis();
        	int index =0;
        	while (historyMapper.deleteAll()!=0) {
        		index++;
    		}
        	long endTime = System.currentTimeMillis();
        	System.out.println("删除数据"+index*1000+"用时"+(endTime-beginTime));
            System.out.println(System.currentTimeMillis()+"   定时任务启动");
    		String fisrtIndex="";
    		String queryIndex="";
    		List<KeyValue> _lList = null;
    		List<HistoryMTInfo> tempList = new ArrayList<HistoryMTInfo>();
    		 _lList= ssdb1.hscan("SOURCE", "", "", 1);
    		 index=0;
    		if (_lList!=null && _lList.size()==1) {
    			fisrtIndex=_lList.get(0).getKey();
    			//插入数据库
    			String temp = _lList.get(0).getValue();
    			HistoryMTInfo _HistoryMTInfo=JSON.parseObject(temp, HistoryMTInfo.class);
    			historyMapper.insert(_HistoryMTInfo);
    			_lList.clear();
    			_lList = ssdb1.hscan("SOURCE", fisrtIndex, "", 5000);
    			while (_lList!=null && _lList.size()>0) {
    				index++;
    				for (KeyValue keyValue2 : _lList) {
    					tempList.add(JSON.parseObject(keyValue2.getValue(), HistoryMTInfo.class));
    				}
    				int endIndex=_lList.size();
    				queryIndex=_lList.get(endIndex-1).getKey();
    				//插入数据库
    				batchInsert(tempList);
    				tempList.clear();
    				_lList.clear();
    				_lList = ssdb1.hscan("SOURCE", queryIndex, "", 5000);
    			}
    		}
    		System.out.println("加载数据"+index*5000+"用时"+(System.currentTimeMillis()-endTime));
    	}else {
    		System.out.println("未在规定的时间内，不拉取数据");
    	}
    	
    }
    public void batchInsert(List<HistoryMTInfo> tempList ) {
		if (tempList.size()>1000) {
			List<HistoryMTInfo> splitList = new ArrayList<>();
			for (HistoryMTInfo historyMTInfo : tempList) {
				if (splitList.size()==1000) {
					//插入数据库
					historyMapper.batchInsert(splitList);
					splitList.clear();
				}else {
					splitList.add(historyMTInfo);
				}
					
			}
			if (splitList.size()>0) {
				historyMapper.batchInsert(splitList);
			}
		}else {
			//插入数据库
			historyMapper.batchInsert(tempList);
		}
			
	}
}
