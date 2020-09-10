/**
 * 
 */
package com.labbol.cocoon.plugin.platform.user.handler;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.dialect.DialectType;
import org.yelong.core.model.sql.SqlModel;

import com.github.pagehelper.PageInfo;
import com.labbol.cocoon.plugin.platform.user.dto.UserDTO;
import com.labbol.core.platform.user.model.User;
import com.labbol.core.service.LabbolModelService;

/**
 * @author PengFei
 *
 */
public class DefaultUserQueryHandler implements UserQueryHandler{

	@Resource
	private LabbolModelService modelService;
	
	protected String getFindUserVOSql() {
		Dialect dialect = modelService.getModelConfiguration().getDialect();
		DialectType dialectType = dialect.getDialectType();
		if(dialectType == DialectType.MYSQL) {
			/*
select DISTINCT usr.* ,
org.orgName usrOrgName, org.orgNo usrOrgNo,
(select group_concat(userrole.roleId) from CO_USER_ROLE userrole where userrole.userId = usr.id) usrRoles  
from CO_USER usr
join co_org org on org.id = usr.orgId
left join co_user_role userRole on userRole.userId = usr.id
left join co_role role on role.id = userRole.roleId
			 */
			return "select DISTINCT usr.* ,\r\n" + 
					"org.orgName usrOrgName, org.orgNo usrOrgNo,\r\n" + 
					"(select group_concat(userrole.roleId) from CO_USER_ROLE userrole where userrole.userId = usr.id) usrRoles  \r\n" + 
					"from CO_USER usr\r\n" + 
					"join co_org org on org.id = usr.orgId\r\n" + 
					"left join co_user_role userRole on userRole.userId = usr.id\r\n" + 
					"left join co_role role on role.id = userRole.roleId";
		} else if(dialectType  == DialectType.ORACLE) {
			return "select usr.*, org.orgName usrOrgName, org.orgNo usrOrgNo, " +
					"(select "
					//					 + "to_char(wm_concat(userrole.roleId)) "
					+ "LISTAGG(userrole.roleId,',') WITHIN GROUP (ORDER BY userrole.roleId) "
					+ "from CO_USER_ROLE userrole where userrole.userId = usr.id) usrRoles  "
					+ "from CO_USER usr  " +
					"inner join CO_ORG org on usr.orgId = org.id and org.state = '0' ";
		} else {
			throw new UnsupportedOperationException("不支持的数据库方言："+dialectType);
		}
	}

	@Override
	public List<? extends User> queryUser(HttpServletRequest request, SqlModel<? extends User> sqlModel) {
		return modelService.findBySqlModel(UserDTO.class,getFindUserVOSql(), sqlModel);
	}

	@Override
	public PageInfo<? extends User> queryUser(HttpServletRequest request, SqlModel<? extends User> sqlModel, Integer pageNum,
			Integer pageSize) {
		String roleNames = request.getParameter("roleNames");
		if(StringUtils.isNotBlank(roleNames)) {
			sqlModel.addCondition("role.roleName", "IN", roleNames.split(","));
		}
		return new PageInfo<>(modelService.findPageBySqlModel(UserDTO.class,getFindUserVOSql(), sqlModel,pageNum,pageSize));
	}

	@Override
	public User getUser(HttpServletRequest request, SqlModel<? extends User> sqlModel) {
		return modelService.findFirstBySqlModel(UserDTO.class,getFindUserVOSql(),sqlModel);
	}


}
