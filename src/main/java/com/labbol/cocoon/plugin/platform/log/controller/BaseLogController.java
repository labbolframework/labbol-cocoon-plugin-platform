/**
 * 
 */
package com.labbol.cocoon.plugin.platform.log.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.labbol.cocoon.controller.BaseCrudSupportController;
import com.labbol.core.platform.log.model.Log;

/**
 * 日志管理器
 * 
 * @since 2.0
 */
@RequestMapping("log")
public abstract class BaseLogController<M extends Log> extends BaseCrudSupportController<M> {

	@RequestMapping("index")
	public String index() {
		return "platform/log/logManage.jsp";
	}

	@Override
	protected void beforeQueryModel(M model) throws Exception {
		model.addConditionOperator("userName", "LIKE");
		String eventType = model.getEventType();
		if (StringUtils.isNotBlank(eventType)) {
			model.addCondition("eventType", "IN", eventType.split(","));
		}
	}

}
