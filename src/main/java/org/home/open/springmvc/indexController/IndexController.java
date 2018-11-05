package org.home.open.springmvc.indexController;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
public class IndexController {
 
    @RequestMapping("/helloworld")
    public String hello(Model model) {
        model.addAttribute("greeting", "Hello Spring MVC wode tian");
        return "helloworld";
    }
    @RequestMapping("/cpuserQuery")
    public String cpuserQuery(Model model) {
        return "cpuserQuery";
    }
    @RequestMapping("/channelQuery")
    public String channelQuery(Model model) {
        return "channelQuery";
         
    }
    @RequestMapping("/historyQuery")
    public String historyQuery(Model model) {
        return "historyQuery";
    }
    //测试一下
    
    @RequestMapping("/index")
    public String index(Model model) {
        return "index";
    }
 
}