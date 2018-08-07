package com.zhx.modules.testpaper.bean;

/**
 * 会员和试卷以及试卷答案关联
 * @author Administrator
 *
 */
public class MemberTPAnswer {

	private String id;
	private String mId;
	private String tpId;
	private String qId;
	private String qAnswer;
	private String submitTime;
	private String seq;
	
	public MemberTPAnswer(){}
	
	public MemberTPAnswer(String id,String mId,String tpId,String qId,String qAnswer,String submitTime,String seq){
		this.id = id;
		this.mId = mId;
		this.tpId = tpId;
		this.qId = qId;
		this.qAnswer = qAnswer;
		this.submitTime = submitTime;
		this.seq = seq;
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
	public String getqId() {
		return qId;
	}
	public void setqId(String qId) {
		this.qId = qId;
	}
	public String getqAnswer() {
		return qAnswer;
	}
	public void setqAnswer(String qAnswer) {
		this.qAnswer = qAnswer;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
}
