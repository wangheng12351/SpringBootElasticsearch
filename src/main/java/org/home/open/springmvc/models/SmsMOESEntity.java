package org.home.open.springmvc.models;

import java.util.Date;

public class SmsMOESEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * IDID
	 */
	private Long id = 0L;
	/**
	 * 发送号码发送号码
	 */
	private String srcnumber = "";
	/**
	 * 特服号特服号
	 */
	private String servicecode = "";
	/**
	 * 扩展码扩展码
	 */
	private String servicecodeadd = "";
	/**
	 * 接收号码接收号码
	 */
	private String descnumber = "";
	/**
	 * 发送内容发送内容
	 */
	private String smscontent = "";
	/**
	 * 接收时间接收时间
	 */
	private Date receivetime = new Date();
	/**
	 * 收取时间收取时间
	 */
	private Date gettime = new Date();
	/**
	 * 通道号通道号
	 */
	private Long channelnumber = 0L;
	/**
	 * 客户ID客户ID(与客户表的ID相对应)
	 */
	private Long userid = 0L;
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

	public String getSrcnumber() {
		return srcnumber;
	}
	public void setSrcnumber(String srcnumber) {
		this.srcnumber = srcnumber;
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
	public String getDescnumber() {
		return descnumber;
	}
	public void setDescnumber(String descnumber) {
		this.descnumber = descnumber;
	}
	public String getSmscontent() {
		return smscontent;
	}
	public void setSmscontent(String smscontent) {
		this.smscontent = smscontent;
	}
	public Date getReceivetime() {
		return receivetime;
	}
	public void setReceivetime(Date receivetime) {
		this.receivetime = receivetime;
	}
	public Date getGettime() {
		return gettime;
	}
	public void setGettime(Date gettime) {
		this.gettime = gettime;
	}
	public Long getChannelnumber() {
		return channelnumber;
	}
	public void setChannelnumber(Long channelnumber) {
		this.channelnumber = channelnumber;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
