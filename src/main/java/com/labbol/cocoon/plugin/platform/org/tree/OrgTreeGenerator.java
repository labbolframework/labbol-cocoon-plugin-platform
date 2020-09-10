package com.labbol.cocoon.plugin.platform.org.tree;

import java.util.List;

import com.labbol.cocoon.extjs.TreeStoreData;
import com.labbol.core.platform.org.model.Org;

/**
 * 组织树生成器
 * 
 * @since 2.0
 */
public interface OrgTreeGenerator {

	List<TreeStoreData<Org>> generateTree(String parentOrgNo, boolean single, boolean showRoot, boolean recursion,
			boolean recursionParent, boolean checkbox);

}
