package com.shanxin.oprt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shanxin.core.api.ApiBo;
import com.shanxin.core.exception.CoreException;
import com.shanxin.core.exception.ServiceException;
import com.shanxin.core.util.MyStringUtils;
import com.shanxin.oprt.dao.OprtDao;
import com.shanxin.oprt.dao.OprtTokenDao;
import com.shanxin.oprt.dao.PermissionDao;
import com.shanxin.oprt.dao.model.OprtModel;
import com.shanxin.oprt.dao.model.OprtTokenModel;
import com.shanxin.oprt.dao.model.PermissionModel;
import com.shanxin.oprt.serivce.entity.request.OprtPermissionCheckRequest;
import com.shanxin.oprt.serivce.entity.response.OprtPermissionCheckResponse;

@Service
public class OprtPermissionCheckBo extends ApiBo<OprtPermissionCheckRequest> {
	@Autowired
	private OprtDao oprtDao;
	@Autowired
	private OprtTokenDao oprtTokenDao;
	@Autowired
	private PermissionDao permissionDao;

	@Override
	public Class<OprtPermissionCheckRequest> getApiRequestType() {
		return OprtPermissionCheckRequest.class;
	}

	@Override
	public void doService() throws ServiceException {
		try {
			OprtPermissionCheckResponse rsp = (OprtPermissionCheckResponse) this.apiResponse;
			rsp.setSuccess(false);
			Map<String, Object> paras = new HashMap<String, Object>();
			paras.clear();

			// 一、操作员：存在，secret
			paras.put("id", this.apiRequest.getOprtId());
			List<OprtModel> oprtModels = this.oprtDao.select(paras, OprtModel.class);
			if (oprtModels.size() <= 0)
				throw new ServiceException("不存在此操作者。");
			if (oprtModels.size() > 1)
				throw new ServiceException("此操作者loginName出现重复记录。");
			OprtModel oprtModel = oprtModels.get(0);
			if (!oprtModel.isFlag())
				throw new ServiceException("此操作者无效。");
			if (MyStringUtils.isEmpty(oprtModel.getSecret()))
				throw new ServiceException("此操作者secret为空。");
			// **数据库不区分大小写，这里区分大小写
			if (!oprtModel.getSecret().equals(this.apiRequest.getOprtSecret()))
				throw new ServiceException("此操作者secret错误。");

			// 二、accessToken验证，先取Permission，是否要accessToken，若不需要跳过
			paras.clear();
			paras.put("code", this.apiRequest.getCheckMethod());
			List<PermissionModel> permissionModels = this.permissionDao.select(paras, PermissionModel.class);
			if (permissionModels.size() > 1)
				throw new ServiceException("权限Permission的code出现重复记录。");
			if (permissionModels.size() > 0 && permissionModels.get(0).isNeedAccessToken()) {
				if (MyStringUtils.isEmpty(this.apiRequest.getOprtAccessToken()))
					throw new ServiceException("此接口需要accessToken，但没传入。");

				// 后查询看是否accessToken过期
				paras.clear();
				paras.put("oprtId", this.apiRequest.getOprtId());
				paras.put("oprtSecret", this.apiRequest.getOprtSecret());
				List<OprtTokenModel> oprtTokenModels = this.oprtTokenDao.select(paras, OprtTokenModel.class);
				if (oprtTokenModels.size() <= 0)
					throw new ServiceException("不存accessToken有误。");
				if (oprtTokenModels.size() > 1)
					throw new ServiceException("对appId,appSecret出现重复记录。");

				OprtTokenModel oprtTokenModel = oprtTokenModels.get(0);
				if (MyStringUtils.isEmpty(oprtTokenModel.getAccessToken()))
					throw new ServiceException("表中accessToken为空。");
				// **数据库不区分大小写，这里区分大小写
				if (!oprtTokenModel.getAccessToken().equals(this.apiRequest.getOprtAccessToken()))
					throw new ServiceException("accessToken有误。", "accessToken-error");
				if ((new Date()).getTime() > oprtTokenModel.getExpiredDate().getTime())
					throw new ServiceException("accessToken过期。", "accessToken-expired");
			}

			// permission权限验证，没有在数据库配置时，通过，数据库有配置时看needCheck
			if (permissionModels.size() > 0 && permissionModels.get(0).isNeedCheck()) {
				PermissionModel permissionModel = permissionDao.getTheOne(this.apiRequest.getOprtId(), this.apiRequest.getCheckMethod());
				if (permissionModel == null)
					throw new ServiceException("没有调有此方法权限。", "permission-deny");
			}

			rsp.setSuccess(true);
		} catch (Throwable ex) {
			if (ex instanceof CoreException)
				throw new ServiceException(ex.getMessage(), ((CoreException) ex).getCode());
			else
				throw new ServiceException(ex.getMessage());
		}
	}
}
