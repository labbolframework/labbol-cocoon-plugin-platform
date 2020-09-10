/**
 * 
 */
package com.labbol.cocoon.plugin.platform.role.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.labbol.cocoon.controller.BaseCrudSupportController;
import com.labbol.core.platform.role.model.RoleDataRight;

/**
 * 数据角色管理器
 * 
 * @since 2.0
 */
@RequestMapping(value = "roleDataRight")
public abstract class BaseRoleDataRightController<M extends RoleDataRight> extends BaseCrudSupportController<M> {

}
