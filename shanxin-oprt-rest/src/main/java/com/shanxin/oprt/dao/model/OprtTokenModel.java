package com.shanxin.oprt.dao.model;

import java.sql.Date;
import java.sql.Timestamp;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

@Table(name = "oprt_token")
public class OprtTokenModel {
	@AutoID
	private int id;
	private int oprtId;
	private String oprtSecret;
	private String accessToken;
	private Date expiredDate;
	private String refreshToken;
	private Timestamp createTime;
	private Timestamp lastUpdateTime;
	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOprtId() {
		return oprtId;
	}

	public void setOprtId(int oprtId) {
		this.oprtId = oprtId;
	}

	public String getOprtSecret() {
		return oprtSecret;
	}

	public void setOprtSecret(String oprtSecret) {
		this.oprtSecret = oprtSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
