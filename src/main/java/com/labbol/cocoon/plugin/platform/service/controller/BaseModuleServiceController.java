package com.labbol.cocoon.plugin.platform.service.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;

import com.labbol.cocoon.controller.BaseCrudSupportController;
import com.labbol.core.platform.service.manage.CacheableModuleServiceManager;
import com.labbol.core.platform.service.manage.ModuleServiceManager;
import com.labbol.core.platform.service.model.ModuleService;

/**
 * 模块服务控制器
 * 
 * @param <M> ModuleService type
 * @since 2.0
 */
@RequestMapping(value = "moduleService")
public abstract class BaseModuleServiceController<M extends ModuleService> extends BaseCrudSupportController<M> {

	@Resource
	protected ModuleServiceManager moduleServiceManager;

	@RequestMapping("index")
	public String index() {
		return "platform/service/moduleServiceManage.jsp";
	}

	@Override
	protected void beforeQueryModel(M model) throws Exception {
		model.addConditionOperator("serviceName", "LIKE");
		model.addConditionOperator("serviceNameEn", "LIKE");
		model.addConditionOperator("serviceCharger", "LIKE");
	}

	@Override
	protected void afterDeleteModel(String deleteIds) throws Exception {
		clearModuleServiceCache();
	}

	@Override
	protected void afterModify(M model) throws Exception {
		clearModuleServiceCache();
	}

	@Override
	protected void afterSave(M model) throws Exception {
		clearModuleServiceCache();
	}

	protected void clearModuleServiceCache() {
		if (moduleServiceManager instanceof CacheableModuleServiceManager) {
			((CacheableModuleServiceManager) moduleServiceManager).clearCache();
		}
	}

}
