package org.home.open.springmvc.mysqlController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Student 控制器
 *
 * @author: @我没有三颗心脏
 * @create: 2018-05-08-下午 20:25
 */
@Controller
public class StudentController {

    @Autowired
    StudentMapper studentMapper;

    @RequestMapping("/listStudent")
    public String listStudent(Model model) {
        List<Student> students = studentMapper.findAll();
        model.addAttribute("students", students);
        return "listStudent";
    }
    @RequestMapping("/insertStudent")
    public void insertStudent() {
        List<Student> students = new ArrayList<Student>();
        Student _Student = new Student();
        _Student.setId(1222);
        _Student.setAge(99);
        _Student.setBirthday(new Date());
        _Student.setName("maokun");
        _Student.setSex("man");
        students.add(_Student); 
        Student _Student1 = new Student();
        _Student1.setId(1232);
        _Student1.setAge(99);
        _Student1.setBirthday(new Date());
        _Student1.setName("maokun");
        _Student1.setSex("man");
        students.add(_Student1); 
        studentMapper.batchInsert(students);
    }
}