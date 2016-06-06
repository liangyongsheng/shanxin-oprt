package com.shanxin.oprt.serivce.entity.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.shanxin.core.api.ApiResponse;
import com.shanxin.core.fastxml.jackson.ser.JsonStr2DatetimeDeserializer;
import com.shanxin.core.fastxml.jackson.ser.JsonStr2DatetimeSerializer;

@JacksonXmlRootElement(localName = "oprtLoginResponse")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(creatorVisibility = Visibility.NONE, fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class OprtLoginResponse extends ApiResponse {
	private Integer oprtId;
	private String oprtSecret;
	private String oprtAccessToken;
	private String oprtRefreshToken;
	private String oprtName;
	@JsonSerialize(using = JsonStr2DatetimeSerializer.class)
	@JsonDeserialize(using = JsonStr2DatetimeDeserializer.class)
	private Date createTime;
	private String remark;

	public Integer getOprtId() {
		return oprtId;
	}

	public void setOprtId(Integer oprtId) {
		this.oprtId = oprtId;
	}

	public String getOprtSecret() {
		return oprtSecret;
	}

	public void setOprtSecret(String oprtSecret) {
		this.oprtSecret = oprtSecret;
	}

	public String getOprtAccessToken() {
		return oprtAccessToken;
	}

	public void setOprtAccessToken(String oprtAccessToken) {
		this.oprtAccessToken = oprtAccessToken;
	}

	public String getOprtRefreshToken() {
		return oprtRefreshToken;
	}

	public void setOprtRefreshToken(String oprtRefreshToken) {
		this.oprtRefreshToken = oprtRefreshToken;
	}

	public String getOprtName() {
		return oprtName;
	}

	public void setOprtName(String oprtName) {
		this.oprtName = oprtName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
