package com.shanxin.oprt.dao.model;

import java.sql.Timestamp;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

@Table(name = "permission")
public class PermissionModel {
	@AutoID
	private int id;
	private String code;
	private String name;
	private boolean needAccessToken;
	private boolean needCheck;
	private Timestamp createTime;
	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNeedAccessToken() {
		return needAccessToken;
	}

	public void setNeedAccessToken(boolean needAccessToken) {
		this.needAccessToken = needAccessToken;
	}

	public boolean isNeedCheck() {
		return needCheck;
	}

	public void setNeedCheck(boolean needCheck) {
		this.needCheck = needCheck;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
