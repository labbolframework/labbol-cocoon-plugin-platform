/**
 * 
 */
package com.labbol.cocoon.plugin.platform.user.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.yelong.core.model.sql.SqlModel;

import com.github.pagehelper.PageInfo;
import com.labbol.core.platform.user.model.User;

/**
 * @author PengFei
 *
 */
public interface UserQueryHandler {

	List<? extends User> queryUser(HttpServletRequest request , SqlModel<? extends User> sqlModel);
	
	PageInfo<? extends User> queryUser(HttpServletRequest request , SqlModel<? extends User> sqlModel , Integer pageNum, Integer pageSize);

	User getUser(HttpServletRequest request , SqlModel<? extends User> sqlModel);
	
}
