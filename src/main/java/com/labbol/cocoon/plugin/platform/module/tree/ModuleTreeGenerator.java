package com.labbol.cocoon.plugin.platform.module.tree;

import java.util.List;

import com.labbol.cocoon.extjs.TreeStoreData;
import com.labbol.core.platform.module.model.Module;

/**
 * 模块树生成器
 * 
 * @since 2.0
 */
public interface ModuleTreeGenerator {

	List<TreeStoreData<Module>> generateTree(ModuleTreeGenerateConfig moduleTreeGenerateConfig);

}
