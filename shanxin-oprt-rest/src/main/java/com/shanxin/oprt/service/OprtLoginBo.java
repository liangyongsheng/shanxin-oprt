package com.shanxin.oprt.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shanxin.core.api.ApiBo;
import com.shanxin.core.exception.CoreException;
import com.shanxin.core.exception.ServiceException;
import com.shanxin.core.util.MyAlgorithmUtils;
import com.shanxin.core.util.MyStringUtils;
import com.shanxin.oprt.dao.OprtDao;
import com.shanxin.oprt.dao.OprtTokenDao;
import com.shanxin.oprt.dao.model.OprtModel;
import com.shanxin.oprt.dao.model.OprtTokenModel;
import com.shanxin.oprt.serivce.entity.request.OprtLoginRequest;
import com.shanxin.oprt.serivce.entity.response.OprtLoginResponse;

@Service
public class OprtLoginBo extends ApiBo<OprtLoginRequest> {
	@Autowired
	private OprtDao oprtDao;
	@Autowired
	private OprtTokenDao oprtTokenDao;

	@Override
	public Class<OprtLoginRequest> getApiRequestType() {
		return OprtLoginRequest.class;
	}

	@Override
	public void doService() throws ServiceException {
		try {

			OprtLoginResponse rsp = (OprtLoginResponse) this.apiResponse;

			Map<String, Object> paras = new HashMap<String, Object>();
			paras.clear();

			// 操作员：存在，密码
			paras.put("loginName", this.apiRequest.getOprtLoginName());
			List<OprtModel> oprtModels = oprtDao.select(paras, OprtModel.class);
			if (oprtModels.size() <= 0)
				throw new ServiceException("不存要此操作员。");
			if (oprtModels.size() > 1)
				throw new ServiceException("操作员loginName出现重复记录。");
			OprtModel oprtModel = oprtModels.get(0);
			if (MyStringUtils.isEmpty(oprtModel.getLoginPwd()))
				throw new ServiceException("此操作员密码为空。");
			// **数据库不区分大小写，这里区分大小写
			if (!oprtModel.getLoginPwd().equals(MyAlgorithmUtils.MD5(this.apiRequest.getOprtLoginPwd())))
				throw new ServiceException("此操作员secret错误。");

			// 取得accessToken
			paras.clear();
			paras.put("oprtId", oprtModel.getId());
			paras.put("oprtSecret", oprtModel.getSecret());
			List<OprtTokenModel> oprtTokenModels = this.oprtTokenDao.select(paras, OprtTokenModel.class);
			OprtTokenModel oprtTokenModel = oprtTokenModels.size() <= 0 ? null : oprtTokenModels.get(0);

			// 若accessToken不存在则产生
			if (oprtTokenModel == null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				oprtTokenModel = new OprtTokenModel();
				oprtTokenModel.setOprtId(oprtModel.getId());
				oprtTokenModel.setOprtSecret(oprtModel.getSecret());
				oprtTokenModel.setAccessToken(MyAlgorithmUtils.MD5(sdf.format(new Date())) + MyAlgorithmUtils.MD5(sdf.format(new Date((new Date()).getTime() + 10L * 365 * 24 * 60 * 60 * 1000))));
				oprtTokenModel.setExpiredDate(new java.sql.Date((new Date()).getTime() + 10L * 365 * 24 * 60 * 60 * 1000));
				oprtTokenModel.setRefreshToken(null); // 不启动
				oprtTokenModel.setCreateTime(new Timestamp((new Date()).getTime()));
				oprtTokenModel.setLastUpdateTime(new Timestamp((new Date()).getTime()));
				oprtTokenModel.setRemark("autogenerate");
				if (this.oprtTokenDao.insert(oprtTokenModel) <= 0)
					throw new ServiceException("自动产生accessToken不成功。");
			}

			rsp.setCreateTime(oprtModel.getCreateTime());
			rsp.setOprtName(oprtModel.getName());
			rsp.setOprtId(oprtModel.getId());
			rsp.setOprtSecret(oprtModel.getSecret());
			rsp.setOprtAccessToken(oprtTokenModel == null ? null : oprtTokenModel.getAccessToken());
			rsp.setRemark(oprtModel.getRemark());
		} catch (Throwable ex) {
			if (ex instanceof CoreException)
				throw new ServiceException(ex.getMessage(), ((CoreException) ex).getCode());
			else
				throw new ServiceException(ex.getMessage());
		}
	}
}
