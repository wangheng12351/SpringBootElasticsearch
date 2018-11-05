package org.home.open.springmvc.ssdbController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.home.open.dao.model.HistoryMTInfo;
import org.home.open.springmvc.mysqlController.HistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.hyd.ssdb.SsdbClient;
import com.hyd.ssdb.util.KeyValue;
/***********************
 * SSDB处理工具类
 * @author Administrator
 *
 */
@RestController
public class SsdbController  {
    @Autowired
    private SsdbClient ssdb1;
    @Autowired
    HistoryMapper historyMapper;
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/ssdb_hgetall")
	@ResponseBody
	public ResponseEntity ssdb_hgetall() {
		Map<String, String> keyvalue=new HashMap<String, String>();
		List<KeyValue> _lList= ssdb1.hgetall("SOURCE");
		for (KeyValue keyValue2 : _lList) {
			keyvalue.put(keyValue2.getKey(), keyValue2.getValue());
		}
		return new ResponseEntity(keyvalue,HttpStatus.OK);
	}
	//可以继续扩展其他命令
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/ssdb_hscan")
	@ResponseBody
	public ResponseEntity ssdb_hscan() {
		while (historyMapper.deleteAll()!=0) {
			System.out.println("删除了100条");
		}
		System.out.println("删除数据总数=  "+historyMapper.deleteAll());
		//获取第一个key 然后根据第一个key陆续往下查询，直到获取完所有的
		Map<String, String> keyvalue=new HashMap<String, String>();
		String fisrtIndex="";
		String queryIndex="";
		List<KeyValue> _lList = null;
		List<HistoryMTInfo> tempList = new ArrayList<HistoryMTInfo>();
		 _lList= ssdb1.hscan("SOURCE", "", "", 1);
		if (_lList!=null && _lList.size()==1) {
			fisrtIndex=_lList.get(0).getKey();
			//插入数据库
			String temp = _lList.get(0).getValue();
			HistoryMTInfo _HistoryMTInfo=JSON.parseObject(temp, HistoryMTInfo.class);
			historyMapper.insert(_HistoryMTInfo);
			_lList.clear();
			_lList = ssdb1.hscan("SOURCE", fisrtIndex, "", 10);
			while (_lList!=null && _lList.size()>0) {
				for (KeyValue keyValue2 : _lList) {
					tempList.add(JSON.parseObject(keyValue2.getValue(), HistoryMTInfo.class));
				}
				int endIndex=_lList.size();
				queryIndex=_lList.get(endIndex-1).getKey();
				//插入数据库
				historyMapper.batchInsert(tempList);
				tempList.clear();
				_lList.clear();
				_lList = ssdb1.hscan("SOURCE", queryIndex, "", 10);
				for (KeyValue keyValue2 : _lList) {
					keyvalue.put(keyValue2.getKey(), keyValue2.getValue());
				}
			}
		}
		return new ResponseEntity(keyvalue,HttpStatus.OK);
	}

}