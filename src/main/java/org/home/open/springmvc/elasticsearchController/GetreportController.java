package org.home.open.springmvc.elasticsearchController;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@SpringBootApplication
@RestController
public class GetreportController  {
    @Autowired
    private TransportClient client;
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/get")
	@ResponseBody
	public ResponseEntity getreport(@RequestParam(name="id",defaultValue="")String id) {
		if(id.isEmpty()){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		GetResponse response = this.client.prepareGet("smsreportinfo", "smsreportinfo", id).get();
		if(!response.isExists()){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(response.getSource(),HttpStatus.OK);
	}
   

}