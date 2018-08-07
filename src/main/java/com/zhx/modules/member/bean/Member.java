package com.zhx.modules.member.bean;

public class Member {

	private String id;
	private String mUserName;
	private String mPwd;
	private String mName;
	private String mPhone;
	private String mCId;
	private String mAddress;
	private String mType;
	private String mWxOpenid;
	private String mWxHeadimg;
	private String createTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getmUserName() {
		return mUserName;
	}
	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}
	public String getmPwd() {
		return mPwd;
	}
	public void setmPwd(String mPwd) {
		this.mPwd = mPwd;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public String getmPhone() {
		return mPhone;
	}
	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}
	public String getmCId() {
		return mCId;
	}
	public void setmCId(String mCId) {
		this.mCId = mCId;
	}
	
	public String getmAddress() {
		return mAddress;
	}
	public void setmAddress(String mAddress) {
		this.mAddress = mAddress;
	}
	public String getmType() {
		return mType;
	}
	public void setmType(String mType) {
		this.mType = mType;
	}
	public String getmWxOpenid() {
		return mWxOpenid;
	}
	public void setmWxOpenid(String mWxOpenid) {
		this.mWxOpenid = mWxOpenid;
	}
	public String getmWxHeadimg() {
		return mWxHeadimg;
	}
	public void setmWxHeadimg(String mWxHeadimg) {
		this.mWxHeadimg = mWxHeadimg;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "Member=[id:"+this.id+",mName:"+this.mName+",mUserName:"+this.mUserName+",mPwd:"+this.mPwd+
				",mAddress:"+this.mAddress+",mPhone:"+this.mPhone+",mCId:"+this.mCId+",mType:"+this.mType+
				",mWxOpenid:"+this.mWxOpenid+",mWxHeadimg:"+this.mWxHeadimg+",createTime:"+this.createTime;
	}
	
}
