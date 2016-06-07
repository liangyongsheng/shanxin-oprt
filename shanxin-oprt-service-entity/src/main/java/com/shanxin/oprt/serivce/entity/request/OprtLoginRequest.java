package com.shanxin.oprt.serivce.entity.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.shanxin.core.api.ApiRequest;
import com.shanxin.core.exception.CheckException;
import com.shanxin.core.exception.CheckIllicitValueException;
import com.shanxin.core.util.MyStringUtils;
import com.shanxin.oprt.serivce.entity.response.OprtLoginResponse;

@JacksonXmlRootElement(localName = "oprtLoginRequest")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(creatorVisibility = Visibility.NONE, fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class OprtLoginRequest extends ApiRequest<OprtLoginResponse> {
	// 对子类要去掉appId及appSecret验测，只需要在此子类加上一个appId_，appSecret_字段(修稀符任意，类型任意)
	@JsonIgnore
	protected Integer oprtId_;
	@JsonIgnore
	protected String oprtSecret_;
	private String oprtLoginName;
	private String oprtLoginPwd;

	public String getOprtLoginName() {
		return oprtLoginName;
	}

	public void setOprtLoginName(String oprtLoginName) {
		this.oprtLoginName = oprtLoginName;
	}

	public String getOprtLoginPwd() {
		return oprtLoginPwd;
	}

	public void setOprtLoginPwd(String oprtLoginPwd) {
		this.oprtLoginPwd = oprtLoginPwd;
	}

	@Override
	public String getLocalMothedName() {
		return "com.shanxin.oprt.login";
	}

	@Override
	public Class<OprtLoginResponse> getApiResponseType() {
		return OprtLoginResponse.class;
	}

	@Override
	public void checkApiParams() throws CheckException {
		if (MyStringUtils.isEmpty(this.oprtLoginName))
			throw new CheckIllicitValueException("field: oprtLoginName, value is empty.");
		if (MyStringUtils.isEmpty(this.oprtLoginPwd))
			throw new CheckIllicitValueException("field: oprtLoginPwd, value is empty.");
	}
}
