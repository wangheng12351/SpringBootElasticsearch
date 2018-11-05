package org.home.open.springmvc.models;

import java.io.Serializable;
import java.util.Date;

/**
 * @author victorzuo
 * @email zuozhiqiang_legend@qq.com
 */
public class SmsReportESEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 状态报告ID自增长的唯一标识ID
	 */
	private Long id = 0L;
	/**
	 * 发送号码通道号
	 */
	private String srcnumber = "";
	/**
	 * 短信ID系统定义返回到客户端,与MT中SMSID相同
	 */
	private Long smsid = 0L;
	/**
	 * 消息ID运营商返回ID
	 */
	private String seqid = "";
	/**
	 * 特服号特服号
	 */
	private String servicecode = "";
	/**
	 * 特服号扩展号特服号扩展号
	 */
	private String servicecodeadd = "";
	/**
	 * 接收号码接收号码
	 */
	private String receivenumber = "";
	/**
	 * 提交时间提交时间
	 */
	private Date submittime = new Date();
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
	private String memo = "";
	/**
	 * 客户ID客户ID(与客户表的ID相对应)
	 */
	private Long userid = 0L;
	/**
	 * 通道ID通道ID
	 */
	private Long channelid = 0L;
	/**
	 * 创建日期
	 */
	private Date createtime = new Date();
	/**
	 * 最后更新日期
	 */
	private Date updatetime = new Date();

	/**
	 * 短信ID的替换字段
	 */
	private String smsidspare = "";
	/**
	 * 通道名称
	 */
	private String channelname ="";
	
	/**
	 * 获取状态报告ID自增长的唯一标识ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置状态报告ID自增长的唯一标识ID
	 */
	public void setId(Long id) {
		this.id = id;
	}


	public String getSrcnumber() {
		return srcnumber;
	}

	public void setSrcnumber(String srcnumber) {
		this.srcnumber = srcnumber;
	}

	public Long getSmsid() {
		return smsid;
	}

	public void setSmsid(Long smsid) {
		this.smsid = smsid;
	}

	public String getSeqid() {
		return seqid;
	}

	public void setSeqid(String seqid) {
		this.seqid = seqid;
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

	public String getReceivenumber() {
		return receivenumber;
	}

	public void setReceivenumber(String receivenumber) {
		this.receivenumber = receivenumber;
	}

	public Date getSubmittime() {
		return submittime;
	}

	public void setSubmittime(Date submittime) {
		this.submittime = submittime;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getChannelid() {
		return channelid;
	}

	public void setChannelid(Long channelid) {
		this.channelid = channelid;
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

	public String getSmsidspare() {
		return smsidspare;
	}

	public void setSmsidspare(String smsidspare) {
		this.smsidspare = smsidspare;
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
