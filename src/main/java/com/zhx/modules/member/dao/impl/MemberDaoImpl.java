package com.zhx.modules.member.dao.impl;

import org.springframework.stereotype.Repository;

import com.zhx.modules.frames.BaseJdbcTemplate;
import com.zhx.modules.frames.GlobalCache;
import com.zhx.modules.member.bean.Member;
import com.zhx.modules.member.dao.MemberDao;
import com.zhx.modules.utils.CreateSqlTools;

@Repository
public class MemberDaoImpl extends BaseJdbcTemplate implements MemberDao {

	/**
	 * web前端登录操作
	 */
	@Override
	public Member selectByUserName(String mUserName) {
		String SELECT_COLUMN = GlobalCache.columnsStrMap4LowerCase.get("t_member");
		String sql="select "+SELECT_COLUMN+" from t_member "
				+ "where m_username='"+mUserName+"' or m_cid='"+mUserName+"' or m_phone='"+mUserName+"'";
		return queryForObject4Custom(sql, Member.class, "t_member");
	}

	/**
	 * 主键查询
	 */
	@Override
	public Member selectById(String mId) {
		String SELECT_COLUMN = GlobalCache.columnsStrMap4LowerCase.get("t_member");
		String sql="select "+SELECT_COLUMN+" from t_member where id='"+mId+"'";
		return queryForObject4Custom(sql, Member.class, "t_member");
	}

	@Override
	public Member selectByOpenId(String openId) {
		String sql = "select id,m_name,m_phone,m_cid,m_wx_openid,m_wx_headimg,m_address from t_member where m_wx_openid='"+openId+"'";
		return queryForObject4Custom(sql, Member.class, "t_member");
	}

	@Override
	public int insertMember(Member member) {
		String sql = CreateSqlTools.getCreateSql(member, Member.class, "t_member");
		return update(sql);
	}

	@Override
	public Member updateMember4Wx(Member member) {
		Member m = null;
		String sql = "update t_member set m_name='"+member.getmName()+"', m_phone='"+member.getmPhone()+"',m_cid='"+member.getmCId()+"',m_address='"+member.getmAddress()+"' where id='"+member.getId()+"'";
		int result = update(sql);
		if(result==1){
			m = selectById(member.getId());
		}
		return m;
	}

	@Override
	public Member selectByCid(String getmCId) {
		String sql = "select id,m_name,m_phone,m_cid,m_wx_openid,m_wx_headimg from t_member where m_cid='"+getmCId+"'";
		return queryForObject4Custom(sql, Member.class, "t_member");
	}

	@Override
	public int updateMember4WxRegister(Member member) {
		String sql = "update t_member set m_phone='"+member.getmPhone()+"',m_wx_openid='"+member.getmWxOpenid()+"',m_wx_headimg='"+member.getmWxHeadimg()+"',m_address='"+member.getmAddress()+"' where id='"+member.getId()+"'";
		return update(sql);
	}

	@Override
	public int updateMember4WebRegister(Member member) {
		String sql = "update t_member set m_phone='"+member.getmPhone()+"',m_username='"+member.getmUserName()
				+"',m_name='"+member.getmName()+"',m_pwd='"+member.getmPwd()+"',m_address='"+member.getmAddress()+"' where id='"+member.getId()+"'";
		return update(sql);
	}

	@Override
	public Member selectByPhoneOrCId(String phoneOrCId) {
		String sql = "select id,m_name,m_phone,m_cid,m_wx_openid,m_wx_headimg from t_member where m_cid='"+phoneOrCId+"' or m_phone='"+phoneOrCId+"'";
		return queryForObject4Custom(sql, Member.class, "t_member");
	}

}
