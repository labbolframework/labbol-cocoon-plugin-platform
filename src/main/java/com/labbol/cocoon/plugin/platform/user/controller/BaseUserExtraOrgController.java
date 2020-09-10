/**
 * 
 */
package com.labbol.cocoon.plugin.platform.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.labbol.cocoon.controller.BaseCrudSupportController;
import com.labbol.cocoon.plugin.platform.user.dto.UserExtraOrgDTO;
import com.labbol.core.platform.user.model.UserExtraOrg;

/**
 * 用户附属部门控制器
 * 
 * @param <M>UserExtraOrg type
 * @since 2.0
 */
@RequestMapping(value = "userExtraOrg")
public abstract class BaseUserExtraOrgController<M extends UserExtraOrg> extends BaseCrudSupportController<M> {

	@ResponseBody
	@RequestMapping(value = "queryUserExtraOrg")
	public String queryUserExtraOrg(@ModelAttribute M model) {
		model.addSortFields(getSortFieldMap());
		Integer pageNum = getPageNum();
		Integer pageSize = getPageSize();
		List<UserExtraOrgDTO> list = modelService.findPageBySqlModel(UserExtraOrgDTO.class, model, pageNum, pageSize);
		return pageInfoToJson(new PageInfo<>(list));
	}

}
