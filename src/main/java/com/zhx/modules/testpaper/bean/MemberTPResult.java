package com.zhx.modules.testpaper.bean;

public class MemberTPResult {

	private String id;
	private String mId;
	private String tpId;
	private String score;
	private String examTime;
	
	public MemberTPResult(String id,String mId,String tpId,String score,String examTime){
		this.id = id;
		this.mId = mId;
		this.tpId = tpId;
		this.score = score;
		this.examTime = examTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String getTpId() {
		return tpId;
	}
	public void setTpId(String tpId) {
		this.tpId = tpId;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getExamTime() {
		return examTime;
	}
	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}
	
}
