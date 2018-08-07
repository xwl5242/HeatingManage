package com.zhx.modules.testpaper.bean;

public class TestPaper {

	private String id;
	private String tpName;
	private String tpTitle;
	private String tpRemark;
	private String isPublish;
	private String creator;
	private String createTime;
	private String updator;
	private String updateTime;
	private String tpIsrandom;
	private String tpRandomNumber;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTpName() {
		return tpName;
	}
	public void setTpName(String tpName) {
		this.tpName = tpName;
	}
	public String getTpTitle() {
		return tpTitle;
	}
	public void setTpTitle(String tpTitle) {
		this.tpTitle = tpTitle;
	}
	public String getTpRemark() {
		return tpRemark;
	}
	public void setTpRemark(String tpRemark) {
		this.tpRemark = tpRemark;
	}
	public String getIsPublish() {
		return isPublish;
	}
	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdator() {
		return updator;
	}
	public void setUpdator(String updator) {
		this.updator = updator;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getTpIsrandom() {
		return tpIsrandom;
	}
	public void setTpIsrandom(String tpIsrandom) {
		this.tpIsrandom = tpIsrandom;
	}
	public String getTpRandomNumber() {
		return tpRandomNumber;
	}
	public void setTpRandomNumber(String tpRandomNumber) {
		this.tpRandomNumber = tpRandomNumber;
	}
	@Override
	public String toString() {
		return "TestPaper:[{id="+this.id+",tpName="+this.tpName+",tpTitle="+this.tpTitle+",tpRemark="+this.tpRemark+",isPublish="+this.isPublish
				+",creator="+this.creator+",createTime="+this.createTime+",updator="+this.updator+",updateTime="+this.updateTime+",tpIsrandom="+
				this.tpIsrandom+",tpRandomNumber="+this.tpRandomNumber+"}]";
	}
	
}
