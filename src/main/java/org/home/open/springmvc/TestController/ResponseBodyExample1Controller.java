package org.home.open.springmvc.TestController;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
 
@Controller
public class ResponseBodyExample1Controller {
 
    // Simple example, method returns String.
    @RequestMapping(value = "/saveResult")
    @ResponseBody
    public String authorInfo() {
        return "saved";
    }
 
     
}