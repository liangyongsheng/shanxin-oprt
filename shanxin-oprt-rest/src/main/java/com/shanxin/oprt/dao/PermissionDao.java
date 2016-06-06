package com.shanxin.oprt.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.shanxin.core.exception.DaoException;
import com.shanxin.oprt.dao.model.PermissionModel;

@Repository
public class PermissionDao extends DaoBase {

	public PermissionModel getTheOne(int oprtId, String code) throws DaoException {
		try {
			Map<String, Object> paras = new HashMap<String, Object>();
			paras.put("oprtId", oprtId);
			paras.put("code", code);
			List<PermissionModel> ls = sqlManager.select("PermissionModel.oprtPermission", PermissionModel.class, paras);
			if (ls.size() > 0)
				return ls.get(0);
			else
				return null;
		} catch (Throwable ex) {
			throw new DaoException(ex.getMessage());
		}
	}
}
