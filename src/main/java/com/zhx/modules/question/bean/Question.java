package com.zhx.modules.question.bean;

public class Question {
	
	private String id;
	private String qTitle;
	private String qType;
	private String qScore;
	private String qSelItem;
	private String qAnswer;
	private String qAwKeyword;
	private String creator;
	private String createTime;
	private String updator;
	private String updateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getqTitle() {
		return qTitle;
	}
	public void setqTitle(String qTitle) {
		this.qTitle = qTitle;
	}
	public String getqType() {
		return qType;
	}
	public void setqType(String qType) {
		this.qType = qType;
	}
	public String getqScore() {
		return qScore;
	}
	public void setqScore(String qScore) {
		this.qScore = qScore;
	}
	public String getqSelItem() {
		return qSelItem;
	}
	public void setqSelItem(String qSelItem) {
		this.qSelItem = qSelItem;
	}
	public String getqAnswer() {
		return qAnswer;
	}
	public void setqAnswer(String qAnswer) {
		this.qAnswer = qAnswer;
	}
	public String getqAwKeyword() {
		return qAwKeyword;
	}
	public void setqAwKeyword(String qAwkeyword) {
		this.qAwKeyword = qAwkeyword;
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
	@Override
	public String toString() {
		return "Question:[{id="+this.id+",qTitle="+this.qTitle+",qType="+this.qType+",qScore="+this.qScore
				+",qSelItem="+this.qSelItem+",qAnswer="+this.qAnswer+",qAwKeyword="+this.qAwKeyword
				+",creator="+this.creator+",createTime="+this.createTime+",updator="+this.updator+",updateTime="+this.updateTime+"}]";
	}

}
