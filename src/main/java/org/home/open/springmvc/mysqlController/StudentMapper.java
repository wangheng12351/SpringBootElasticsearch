package org.home.open.springmvc.mysqlController;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM student")
    List<Student> findAll();
    
    @InsertProvider(type = Provider.class, method = "batchInsert")
    int batchInsert(List<Student> students);
    class Provider {
        /* 批量插入 */
        public String batchInsert(Map<String, List<Student>> map) {
            List<Student> students = map.get("list");
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO student (id,student_id,name,age,sex,birthday) VALUES ");
            MessageFormat mf = new MessageFormat(
                    "(#'{'list[{0}].id}, #'{'list[{0}].student_id}, #'{'list[{0}].name}, #'{'list[{0}].age}, #'{'list[{0}].sex}, #'{'list[{0}].birthday})"
            );

            for (int i = 0; i < students.size(); i++) {
                sb.append(mf.format(new Object[] {i}));
                if (i < students.size() - 1)
                    sb.append(",");
            }
            return sb.toString();
        }
    }

}