package com.shanxin.oprt.serivce.entity.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.shanxin.core.api.ApiRequest;
import com.shanxin.core.exception.CheckException;
import com.shanxin.core.exception.CheckIllicitValueException;
import com.shanxin.core.util.MyStringUtils;
import com.shanxin.oprt.serivce.entity.response.OprtPermissionCheckResponse;

@JacksonXmlRootElement(localName = "oprtPermissionCheckRequest")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(creatorVisibility = Visibility.NONE, fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class OprtPermissionCheckRequest extends ApiRequest<OprtPermissionCheckResponse> {

	private String checkMethod;

	public String getCheckMethod() {
		return checkMethod;
	}

	public void setCheckMethod(String checkMethod) {
		this.checkMethod = checkMethod;
	}

	@Override
	public String getLocalMothedName() {
		return "com.shanxin.oprt.permission.check";
	}

	@Override
	public Class<OprtPermissionCheckResponse> getApiResponseType() {
		return OprtPermissionCheckResponse.class;
	}

	@Override
	public void checkApiParams() throws CheckException {
		if (MyStringUtils.isEmpty(this.checkMethod))
			throw new CheckIllicitValueException("field: checkMethod, value is empty.");
	}
}
