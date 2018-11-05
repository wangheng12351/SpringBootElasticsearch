package org.home.open.springmvc.mysqlController;
import java.util.ArrayList;
import java.util.List;

import org.home.open.dao.model.HistoryMTInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;

/**
 * Student 控制器
 *
 * @create: 2018-05-08-下午 20:25
 */
@Controller
public class HisttoryController {

    @Autowired
    HistoryMapper historyMapper;

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/listHist")
    public ResponseEntity listHist(@RequestParam(name = "channelId", defaultValue = "", required = true) Long channelId) {
        List<HistoryMTInfo> HistoryMTInfo = historyMapper.findById(channelId);
        return new ResponseEntity(JSON.toJSONString(HistoryMTInfo), HttpStatus.OK);
    }
    @RequestMapping("/insertHist")
    public void insertHist() {
        List<HistoryMTInfo> students = new ArrayList<HistoryMTInfo>();
        HistoryMTInfo _Student = new HistoryMTInfo();
        _Student.setChannelID(1L);
        students.add(_Student); 
        HistoryMTInfo _Student1 = new HistoryMTInfo();
        _Student1.setChannelID(2L);
        historyMapper.batchInsert(students);
    }
}