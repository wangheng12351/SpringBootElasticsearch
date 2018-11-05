package org.home.open.springmvc.mysqlController;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.home.open.dao.model.HistoryMTInfo;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface HistoryMapper {

    @Select("SELECT * FROM historymtinfotmp where channelId=#{channelId} order by createTime desc limit 100")
    List<HistoryMTInfo> findById(Long channelId);
    
    @Insert("INSERT INTO historymtinfotmp (SmsID,PackID,PacksID,SeqId,DataSrc,ServiceCode,ServiceCodeAdd,SmsContent,SubmitTime,Number,SchTime,UserPri,Pri,Status,ChannelID,OperatorType,UserID,OperatorUserID,SerialNumber,Memo,GetDataTempId,PkNumber,PkTotal,Rundom,UserPrice,ChannelPrice,PlatformProfit,OrderId,CreateTime,UpdateTime) VALUES (#{smsID}, #{packID}, #{packsID}, #{seqId}, #{dataSrc}, #{serviceCode},#{serviceCodeAdd}, #{smsContent}, #{submitTime}, #{number},#{schTime}, #{userPri}, #{pri}, #{status}, #{channelID}, #{operatorType},#{userID}, #{operatorUserID}, #{serialNumber}, #{memo}, #{getDataTempId},#{pkNumber}, #{pkTotal}, #{rundom}, #{userPrice}, #{channelPrice}, #{platformProfit},#{orderId},#{createTime},#{updateTime})")
    int insert(HistoryMTInfo stu);

    @InsertProvider(type = HistoryProvider.class, method = "batchInsert")
    int batchInsert(@Param("list")List<HistoryMTInfo> students);


    @Delete("delete from historymtinfotmp limit 1000 ")
    int deleteAll();


    class HistoryProvider {
        /* 批量插入 */
		public String batchInsert(Map<String, List<HistoryMTInfo>> map) {
            List<HistoryMTInfo> students = (List<HistoryMTInfo>) map.get("list");
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO historymtinfotmp (SmsID, PackID, PacksID, SeqId,DataSrc, ServiceCode, "
            		+ "ServiceCodeAdd, SmsContent, SubmitTime, Number, "
            		+ "SchTime, UserPri, Pri, Status, ChannelID, OperatorType, "
            		+ "UserID, OperatorUserID, SerialNumber, Memo, GetDataTempId, "
            		+ "PkNumber, PkTotal, Rundom, UserPrice, ChannelPrice, PlatformProfit,"
            		+ " OrderId, CreateTime, UpdateTime)  VALUES ");
            MessageFormat mf = new MessageFormat(
                    "(#'{'list[{0}].smsID}, #'{'list[{0}].packID}, #'{'list[{0}].packsID}, #'{'list[{0}].seqId}, #'{'list[{0}].dataSrc}, #'{'list[{0}].serviceCode}, "
                    + "#'{'list[{0}].serviceCodeAdd}, #'{'list[{0}].smsContent}, #'{'list[{0}].submitTime}, #'{'list[{0}].number}, "
                    + "#'{'list[{0}].schTime}, #'{'list[{0}].userPri}, #'{'list[{0}].pri}, #'{'list[{0}].status}, #'{'list[{0}].channelID}, #'{'list[{0}].operatorType}, "
                    + "#'{'list[{0}].userID}, #'{'list[{0}].operatorUserID}, #'{'list[{0}].serialNumber}, #'{'list[{0}].memo}, #'{'list[{0}].getDataTempId}, "
                    + "#'{'list[{0}].pkNumber}, #'{'list[{0}].pkTotal}, #'{'list[{0}].rundom}, #'{'list[{0}].userPrice}, #'{'list[{0}].channelPrice}, #'{'list[{0}].platformProfit}, "
                    + "#'{'list[{0}].orderId},#'{'list[{0}].createTime},#'{'list[{0}].updateTime})"
            );

            for (int i = 0; i < students.size(); i++) {
                sb.append(mf.format(new Object[] {i}));
                if (i < students.size() - 1)
                    sb.append(",");
            }
            return sb.toString();
        }

        /* 批量删除 
        public String batchDelete(Map map) {
            List<Student> students = (List<Student>) map.get("list");
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM student WHERE id IN (");
            for (int i = 0; i < students.size(); i++) {
                sb.append("'").append(students.get(i).getId()).append("'");
                if (i < students.size() - 1)
                    sb.append(",");
            }
            sb.append(")");
            return sb.toString();
        }*/
    }

}