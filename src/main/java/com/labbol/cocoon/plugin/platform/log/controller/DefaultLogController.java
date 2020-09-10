/**
 * 
 */
package com.labbol.cocoon.plugin.platform.log.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.labbol.core.log.LogRecordUtils;
import com.labbol.core.model.BaseModels;
import com.labbol.core.platform.log.model.Log;

/**
 * 默认的日志管理器
 * 
 * @since 2.0
 */
@Controller
public class DefaultLogController extends BaseLogController<Log> {

	@RequestMapping(value = "loginIndex")
	public String loginIndex() {
		return "platform/log/loginLogManage.jsp";
	}

	@RequestMapping(value = "operationIndex")
	public String operationIndex() {
		return "platform/log/operationLogManage.jsp";
	}

	/**
	 * 查询操作日志
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "queryOperationLog")
	@ResponseBody
	public String queryOperationLog(@ModelAttribute Log model) {
		model.setEventType("02");
		BaseModels.addConditionOperator(model, "userName", "LIKE", "%${value}%");
		Map<String, String> sortFieldMap = getSortFieldMap();
		for (Entry<String, String> entry : sortFieldMap.entrySet()) {
			// model.addSortField(entry.getKey(), entry.getValue());
			addModelSortField(model, entry.getKey(), entry.getValue());
		}
		Integer pageNum = Integer.valueOf(getRequest().getParameter("page"));
		Integer pageSize = Integer.valueOf(getRequest().getParameter("limit"));
		List<Log> pageInfo = modelService.findPageBySqlModel(Log.class, model, pageNum, pageSize);
		LogRecordUtils.setResponseParams("--");
		return pageInfoToJson(new PageInfo<>(pageInfo));

	}

	/**
	 * 查询登录日志
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "queryLoginLog")
	@ResponseBody
	public String queryLoginLog(@ModelAttribute Log model) {
		BaseModels.addConditionOperator(model, "userName", "LIKE", "%${value}%");
		model.setEventType("01");
		Map<String, String> sortFieldMap = getSortFieldMap();
		for (Entry<String, String> entry : sortFieldMap.entrySet()) {
			// model.addSortField(entry.getKey(), entry.getValue());
			addModelSortField(model, entry.getKey(), entry.getValue());
		}
		Integer pageNum = Integer.valueOf(getRequest().getParameter("page"));
		Integer pageSize = Integer.valueOf(getRequest().getParameter("limit"));
		List<Log> pageInfo = modelService.findPageBySqlModel(Log.class, model, pageNum, pageSize);
		LogRecordUtils.setResponseParams("--");
		return pageInfoToJson(new PageInfo<>(pageInfo));
	}

}
