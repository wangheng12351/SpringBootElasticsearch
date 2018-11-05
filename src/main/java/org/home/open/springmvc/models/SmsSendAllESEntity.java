package org.home.open.springmvc.models;

import java.util.Date;

public class SmsSendAllESEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键主键
	 */
	private Long id = 0L;
	/**
	 * MT主键MT主键
	 */
	private Long smsmtid = 0L;
	/**
	 * 短信ID系统定义返回到客户端
	 */
	private Long smsid = 0L;
	/**
	 * 短信包ID短信包ID(客户自定义)
	 */
	private String packid = "";
	/**
	 * 短信批次ID短信批次ID(客户自定义)
	 */
	private String packsid = "";
	/**
	 * 运营商返回运营商返回
	 */
	private String seqid = "";
	/**
	 * 数据来源DBSDK、BS、JAR、WebService、Http
	 */
	private String datasrc = "";
	/**
	 * 特服号特服号
	 */
	private String servicecode = "";
	/**
	 * 特服号扩展码特服号扩展码
	 */
	private String servicecodeadd = "";
	/**
	 * 短信内容短信内容
	 */
	private String smscontent = "";
	/**
	 * 提交时间提交时间
	 */
	private Date smsmtsubmittime = new Date();
	/**
	 * 手机号手机号
	 */
	private String number = "";
	/**
	 * 定时时间定时时间
	 */
	private Date schtime = new Date();
	/**
	 * 用户优先级用户优先级
	 */
	private Integer userpri = 0;
	/**
	 * 发送优先级发送优先级
	 */
	private Integer pri = 0;
	/**
	 * 状态0成功1失败
	 */
	private Integer status = 0;
	/**
	 * 通道ID通道ID
	 */
	private Long channelid = 0L;
	/**
	 * 运营商标识运营商标识(1001移动 1002联通 1003电信)
	 */
	private String operatortype = "";
	/**
	 * 客户ID客户ID(与客户表的ID相对应)
	 */
	private Long smsmtuserid = 0L;
	/**
	 * 操作员ID操作员ID(与用户表的ID相对应)
	 */
	private Long operatoruserid = 0L;
	/**
	 * 发送计费流水号发送计费流水号系统生成32位唯一值
	 */
	private String serialnumber = "";
	/**
	 * 备注备注、扣量回写时使用
	 */
	private String smsmtmemo = "";
	/**
	 * 临时抓取数据ID临时抓取数据ID
	 */
	private Long getdatatempid = 0L;
	/**
	 * 创建日期创建日期
	 */
	private Date smsmtcreatetime = new Date();
	/**
	 * 最后更新日期最后更新日期
	 */
	private Date smsmtupdatetime = new Date();
	/**
	 * 状态报告ID自增长的唯一标识ID
	 */
	private Long smsreportid = 0L;
	/**
	 * 发送号码通道号
	 */
	private String srcnumber = "";
	/**
	 * 接收号码接收号码
	 */
	private String receivenumber = "";
	/**
	 * 提交时间提交时间
	 */
	private Date smsreportsubmittime = new Date();
	/**
	 * 接收时间接收时间
	 */
	private Date receivetime = new Date();
	/**
	 * 发送时间发送时间
	 */
	private Date sendtime = new Date();
	/**
	 * 发送状态0成功 1失败
	 */
	private Integer sendstatus = 0;
	/**
	 * 状态报告值状态报告值
	 */
	private String statusvalue = "";
	/**
	 * 备注99人工生成，
	 */
	private String smsreportmemo = "";
	/**
	 * 客户ID客户ID(与客户表的ID相对应)
	 */
	private Long smsreportuserid = 0L;
	/**
	 * 创建日期创建日期
	 */
	private Date smsreportcreatetime = new Date();
	/**
	 * 最后更新日期最后更新日期
	 */
	private Date smsreportupdatetime = new Date();
	/**
	 * 条数条数
	 */
	private Integer smscount = 0;
	/**
	 * 地区地区省份
	 */
	private String smsarea = "";
	/**
	 * 类型类型
	 */
	private Integer type = 0;
	/**
	 * 备注备注
	 */
	private String memo = "";
	/**
	 * 通道名称
	 */
	private String channelname ="";
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getSmsmtid() {
		return smsmtid;
	}
	public void setSmsmtid(Long smsmtid) {
		this.smsmtid = smsmtid;
	}
	public Long getSmsid() {
		return smsid;
	}
	public void setSmsid(Long smsid) {
		this.smsid = smsid;
	}
	public String getPackid() {
		return packid;
	}
	public void setPackid(String packid) {
		this.packid = packid;
	}
	public String getPacksid() {
		return packsid;
	}
	public void setPacksid(String packsid) {
		this.packsid = packsid;
	}
	public String getSeqid() {
		return seqid;
	}
	public void setSeqid(String seqid) {
		this.seqid = seqid;
	}
	public String getDatasrc() {
		return datasrc;
	}
	public void setDatasrc(String datasrc) {
		this.datasrc = datasrc;
	}
	public String getServicecode() {
		return servicecode;
	}
	public void setServicecode(String servicecode) {
		this.servicecode = servicecode;
	}
	public String getServicecodeadd() {
		return servicecodeadd;
	}
	public void setServicecodeadd(String servicecodeadd) {
		this.servicecodeadd = servicecodeadd;
	}
	public String getSmscontent() {
		return smscontent;
	}
	public void setSmscontent(String smscontent) {
		this.smscontent = smscontent;
	}
	
	public Date getSmsmtsubmittime() {
		return smsmtsubmittime;
	}
	public void setSmsmtsubmittime(Date smsmtsubmittime) {
		this.smsmtsubmittime = smsmtsubmittime;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Date getSchtime() {
		return schtime;
	}
	public void setSchtime(Date schtime) {
		this.schtime = schtime;
	}
	public Integer getUserpri() {
		return userpri;
	}
	public void setUserpri(Integer userpri) {
		this.userpri = userpri;
	}
	public Integer getPri() {
		return pri;
	}
	public void setPri(Integer pri) {
		this.pri = pri;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getChannelid() {
		return channelid;
	}
	public void setChannelid(Long channelid) {
		this.channelid = channelid;
	}
	public String getOperatortype() {
		return operatortype;
	}
	public void setOperatortype(String operatortype) {
		this.operatortype = operatortype;
	}
	public Long getSmsmtuserid() {
		return smsmtuserid;
	}
	public void setSmsmtuserid(Long smsmtuserid) {
		this.smsmtuserid = smsmtuserid;
	}
	public Long getOperatoruserid() {
		return operatoruserid;
	}
	public void setOperatoruserid(Long operatoruserid) {
		this.operatoruserid = operatoruserid;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public String getSmsmtmemo() {
		return smsmtmemo;
	}
	public void setSmsmtmemo(String smsmtmemo) {
		this.smsmtmemo = smsmtmemo;
	}
	public Long getGetdatatempid() {
		return getdatatempid;
	}
	public void setGetdatatempid(Long getdatatempid) {
		this.getdatatempid = getdatatempid;
	}
	public Date getSmsmtcreatetime() {
		return smsmtcreatetime;
	}
	public void setSmsmtcreatetime(Date smsmtcreatetime) {
		this.smsmtcreatetime = smsmtcreatetime;
	}
	public Date getSmsmtupdatetime() {
		return smsmtupdatetime;
	}
	public void setSmsmtupdatetime(Date smsmtupdatetime) {
		this.smsmtupdatetime = smsmtupdatetime;
	}
	public Long getSmsreportid() {
		return smsreportid;
	}
	public void setSmsreportid(Long smsreportid) {
		this.smsreportid = smsreportid;
	}
	public String getSrcnumber() {
		return srcnumber;
	}
	public void setSrcnumber(String srcnumber) {
		this.srcnumber = srcnumber;
	}
	public String getReceivenumber() {
		return receivenumber;
	}
	public void setReceivenumber(String receivenumber) {
		this.receivenumber = receivenumber;
	}
	public Date getSmsreportsubmittime() {
		return smsreportsubmittime;
	}
	public void setSmsreportsubmittime(Date smsreportsubmittime) {
		this.smsreportsubmittime = smsreportsubmittime;
	}
	public Date getReceivetime() {
		return receivetime;
	}
	public void setReceivetime(Date receivetime) {
		this.receivetime = receivetime;
	}
	public Date getSendtime() {
		return sendtime;
	}
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	public Integer getSendstatus() {
		return sendstatus;
	}
	public void setSendstatus(Integer sendstatus) {
		this.sendstatus = sendstatus;
	}
	public String getStatusvalue() {
		return statusvalue;
	}
	public void setStatusvalue(String statusvalue) {
		this.statusvalue = statusvalue;
	}
	public String getSmsreportmemo() {
		return smsreportmemo;
	}
	public void setSmsreportmemo(String smsreportmemo) {
		this.smsreportmemo = smsreportmemo;
	}
	public Long getSmsreportuserid() {
		return smsreportuserid;
	}
	public void setSmsreportuserid(Long smsreportuserid) {
		this.smsreportuserid = smsreportuserid;
	}
	public Date getSmsreportcreatetime() {
		return smsreportcreatetime;
	}
	public void setSmsreportcreatetime(Date smsreportcreatetime) {
		this.smsreportcreatetime = smsreportcreatetime;
	}
	public Date getSmsreportupdatetime() {
		return smsreportupdatetime;
	}
	public void setSmsreportupdatetime(Date smsreportupdatetime) {
		this.smsreportupdatetime = smsreportupdatetime;
	}
	public Integer getSmscount() {
		return smscount;
	}
	public void setSmscount(Integer smscount) {
		this.smscount = smscount;
	}
	public String getSmsarea() {
		return smsarea;
	}
	public void setSmsarea(String smsarea) {
		this.smsarea = smsarea;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getChannelname() {
		return channelname;
	}
	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
