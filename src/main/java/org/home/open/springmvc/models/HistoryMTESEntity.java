package org.home.open.springmvc.models;

import java.util.Date;

public class HistoryMTESEntity {
	/**
	 * 主键主键
	 */
	private Long id = 0L;
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
	private Date submittime = new Date();
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
	private Long userid = 0L;
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
	private String memo = "";
	/**
	 * 临时抓取数据ID临时抓取数据ID
	 */
	private Long getdatatempid = 0L;
	/**
	 * 创建日期
	 */
	private Date createtime = new Date();
	/**
	 * 最后更新日期
	 */
	private Date updatetime = new Date();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Date getSubmittime() {
		return submittime;
	}
	public void setSubmittime(Date submittime) {
		this.submittime = submittime;
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
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getGetdatatempid() {
		return getdatatempid;
	}
	public void setGetDatatempId(Long getdatatempid) {
		this.getdatatempid = getdatatempid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}	

}
