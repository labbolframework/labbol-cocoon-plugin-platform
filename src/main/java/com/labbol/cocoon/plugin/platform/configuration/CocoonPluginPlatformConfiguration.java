/**
 * 
 */
package com.labbol.cocoon.plugin.platform.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.labbol.cocoon.plugin.platform.dict.controller.BaseDictController;
import com.labbol.cocoon.plugin.platform.dict.controller.DefaultDictController;
import com.labbol.cocoon.plugin.platform.filter.controller.BaseFilterController;
import com.labbol.cocoon.plugin.platform.filter.controller.DefaultFilterController;
import com.labbol.cocoon.plugin.platform.icon.controller.BaseIconController;
import com.labbol.cocoon.plugin.platform.icon.controller.DefaultIconController;
import com.labbol.cocoon.plugin.platform.log.controller.BaseLogController;
import com.labbol.cocoon.plugin.platform.log.controller.DefaultLogController;
import com.labbol.cocoon.plugin.platform.module.controller.BaseModuleController;
import com.labbol.cocoon.plugin.platform.module.controller.DefaultModuleController;
import com.labbol.cocoon.plugin.platform.module.tree.DefaultModuleTreeGenerator;
import com.labbol.cocoon.plugin.platform.module.tree.ModuleTreeGenerator;
import com.labbol.cocoon.plugin.platform.org.controller.BaseOrgController;
import com.labbol.cocoon.plugin.platform.org.controller.DefaultOrgController;
import com.labbol.cocoon.plugin.platform.org.tree.DefaultOrgTreeGenerator;
import com.labbol.cocoon.plugin.platform.org.tree.OrgTreeGenerator;
import com.labbol.cocoon.plugin.platform.role.controller.BaseRoleController;
import com.labbol.cocoon.plugin.platform.role.controller.BaseRoleDataRightController;
import com.labbol.cocoon.plugin.platform.role.controller.DefaultRoleController;
import com.labbol.cocoon.plugin.platform.role.controller.DefaultRoleDataRightController;
import com.labbol.cocoon.plugin.platform.scip.controller.BaseSCIPController;
import com.labbol.cocoon.plugin.platform.scip.controller.DefaultSCIPController;
import com.labbol.cocoon.plugin.platform.security.controller.SecurityServiceController;
import com.labbol.cocoon.plugin.platform.service.controller.BaseModuleServiceController;
import com.labbol.cocoon.plugin.platform.service.controller.BaseModuleServiceInterfaceController;
import com.labbol.cocoon.plugin.platform.service.controller.DefaultModuleServiceController;
import com.labbol.cocoon.plugin.platform.service.controller.DefaultModuleServiceInterfaceController;
import com.labbol.cocoon.plugin.platform.user.controller.BaseUserController;
import com.labbol.cocoon.plugin.platform.user.controller.BaseUserExtraOrgController;
import com.labbol.cocoon.plugin.platform.user.controller.DefaultUserController;
import com.labbol.cocoon.plugin.platform.user.controller.DefaultUserExtraOrgController;
import com.labbol.cocoon.plugin.platform.user.handler.DefaultUserQueryHandler;
import com.labbol.cocoon.plugin.platform.user.handler.UserQueryHandler;
import com.labbol.cocoon.plugin.platform.user.service.UserService;
import com.labbol.cocoon.plugin.platform.user.service.impl.UserServiceImpl;
import com.labbol.core.platform.module.manage.ModuleManager;
import com.labbol.core.platform.org.manage.OrgManager;
import com.labbol.core.platform.user.service.UserCommonService;

public class CocoonPluginPlatformConfiguration {

	@Bean
	@ConditionalOnMissingBean(OrgTreeGenerator.class)
	public OrgTreeGenerator orgTreeGenerator(OrgManager orgManager) {
		return new DefaultOrgTreeGenerator(orgManager);
	}

	@Bean
	@ConditionalOnMissingBean(ModuleTreeGenerator.class)
	public ModuleTreeGenerator moduleTreeGenerator(ModuleManager moduleManager) {
		return new DefaultModuleTreeGenerator(moduleManager);
	}

	@Bean
	@ConditionalOnMissingBean(UserService.class)
	public UserService userService(UserCommonService userCommonService) {
		return new UserServiceImpl(userCommonService);
	}

	@Bean
	@ConditionalOnMissingBean(UserQueryHandler.class)
	public UserQueryHandler userQueryHandler() {
		return new DefaultUserQueryHandler();
	}

	// ==================================================controller==================================================

	@Bean
	@ConditionalOnMissingBean(BaseDictController.class)
	public DefaultDictController defaultDictController() {
		return new DefaultDictController();
	}

	@Bean
	@ConditionalOnMissingBean(BaseFilterController.class)
	public DefaultFilterController defaultFilterController() {
		return new DefaultFilterController();
	}

	@Bean
	@ConditionalOnMissingBean(BaseIconController.class)
	public DefaultIconController defaultIconController() {
		return new DefaultIconController();
	}

	@Bean
	@ConditionalOnMissingBean(BaseLogController.class)
	public DefaultLogController defaultLogController() {
		return new DefaultLogController();
	}

	@Bean
	@ConditionalOnMissingBean(BaseModuleController.class)
	public DefaultModuleController defaultModuleController() {
		return new DefaultModuleController();
	}

	@Bean
	@ConditionalOnMissingBean(BaseOrgController.class)
	public DefaultOrgController defaultOrgController() {
		return new DefaultOrgController();
	}

	@Bean
	@ConditionalOnMissingBean(BaseRoleController.class)
	public DefaultRoleController defaultRoleController() {
		return new DefaultRoleController();
	}

	@Bean
	@ConditionalOnMissingBean(BaseRoleDataRightController.class)
	public DefaultRoleDataRightController defaultRoleDataRightController() {
		return new DefaultRoleDataRightController();
	}

	@Bean
	@ConditionalOnMissingBean(SecurityServiceController.class)
	public SecurityServiceController securityServiceController() {
		return new SecurityServiceController();
	}

	@Bean
	@ConditionalOnMissingBean(BaseModuleServiceController.class)
	public DefaultModuleServiceController defaultModuleServiceController() {
		return new DefaultModuleServiceController();
	}

	@Bean
	@ConditionalOnMissingBean(BaseModuleServiceInterfaceController.class)
	public DefaultModuleServiceInterfaceController defaultModuleServiceInterfaceController() {
		return new DefaultModuleServiceInterfaceController();
	}

	@Bean
	@ConditionalOnMissingBean(BaseUserController.class)
	public DefaultUserController defaultUserController() {
		return new DefaultUserController();
	}

	@Bean
	@ConditionalOnMissingBean(BaseUserExtraOrgController.class)
	public DefaultUserExtraOrgController defaultUserExtraOrgController() {
		return new DefaultUserExtraOrgController();
	}

	@Bean
	@ConditionalOnMissingBean(BaseSCIPController.class)
	public DefaultSCIPController defaultSCIPController() {
		return new DefaultSCIPController();
	}

}
