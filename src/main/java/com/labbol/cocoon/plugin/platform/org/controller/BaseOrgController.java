/**
 * 
 */
package com.labbol.cocoon.plugin.platform.org.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.labbol.cocoon.controller.BaseCrudSupportController;
import com.labbol.cocoon.extjs.TreeStoreData;
import com.labbol.cocoon.msg.JsonMsg;
import com.labbol.cocoon.plugin.platform.org.tree.OrgTreeGenerator;
import com.labbol.core.platform.org.Orgs;
import com.labbol.core.platform.org.manage.CacheableOrgManager;
import com.labbol.core.platform.org.manage.OrgManager;
import com.labbol.core.platform.org.model.Org;
import com.labbol.core.platform.org.service.OrgCommonService;

/**
 * 基础的组织控制器
 * 
 * @since 2.0
 */
@RequestMapping("org")
public abstract class BaseOrgController<M extends Org> extends BaseCrudSupportController<M> {

	@Resource
	protected OrgCommonService orgCommonService;

	@Resource
	protected OrgManager orgManager;

	@Resource
	protected OrgTreeGenerator orgTreeGenerator;

	@RequestMapping("index")
	public String index() {
		return "platform/org/orgManage.jsp";
	}

	/**
	 * 获取组织树
	 */
	@ResponseBody
	@RequestMapping("getOrgTree")
	public String getOrgTree() {
		String parentOrgNo = getParameter("parentOrgNo", "-1");
		boolean single = getParameterBoolean("single", false);
		boolean showRoot = ((single) || ("-1".equals(parentOrgNo))) && (getParameterBoolean("showRoot", false));
		boolean recursion = getParameterBoolean("recursion", false);
		boolean recursionParent = getParameterBoolean("recursionParent", true);// 递归显示父
		boolean checkbox = getParameterBoolean("checkbox", false);
		parentOrgNo = (StringUtils.isBlank(parentOrgNo)) || ("-1".equals(parentOrgNo)) ? Orgs.ROOT_ORG_NO : parentOrgNo;
		List<TreeStoreData<Org>> treeStoreDatas = orgTreeGenerator.generateTree(parentOrgNo, single, showRoot,
				recursion, recursionParent, checkbox);
		return null == treeStoreDatas ? "[]" : toJson(treeStoreDatas);
	}

	/**
	 * 查询组织的子组织数量
	 */
	@ResponseBody
	@RequestMapping(value = "queryOrgChildOrgCount")
	public String queryOrgChildOrgCount() {
		JsonMsg msg = new JsonMsg(true, "0");
		String orgNo = getRequest().getParameter("orgNo");
		if (StringUtils.isBlank(orgNo)) {

		} else {
			Org sqlModel = new Org();
			sqlModel.setOrgNo(orgNo + "%");
			sqlModel.addConditionOperator("orgNo", "LIKE");
			Long count = modelService.countBySqlModel(Org.class, sqlModel);
			msg.setMsg("" + count + "");
		}
		return toJson(msg);
	}

	@Override
	protected void beforeQueryModel(M model) throws Exception {
		model.addConditionOperator("orgName", "like");
	}

	@Override
	protected boolean validateModel(M model, JsonMsg msg) throws Exception {
		if (model.getOrgNo().equals(model.getParentOrgNo())) {
			msg.setMsg("上级机构不能为自己！");
			return false;
		}
		return true;
	}

	@Override
	protected void saveModel(M model) throws Exception {
		String orgNo = orgCommonService.generateOrgNo(model.getParentOrgNo());
		model.setOrgNo(orgNo);
		orgCommonService.saveOrg(model);
	}

	@Override
	protected void modifyModel(M model) throws Exception {
		if (!model.getParentOrgNo().equals(model.getOldParentOrgNo())) {
			String orgNo = orgCommonService.generateOrgNo(model.getParentOrgNo());
			model.setOldOrgNo(model.getOrgNo());
			model.setOrgNo(orgNo);
		}
		orgCommonService.modifyOrg(model);
	}

	@Override
	protected boolean deleteModel(String deleteIds) throws Exception {
		if (StringUtils.isNotBlank(deleteIds)) {
			orgCommonService.removeOrgByOrgNos(deleteIds.split(","));
		}
		return true;
	}

	@Override
	protected M retrieveModel(M model) throws Exception {
		String sql = "select org.*, org2.orgName orgParentOrgName from CO_ORG org left join CO_ORG org2 on org.parentorgno = org2.orgno and org2.state = '0'";
		return modelService.findFirstBySqlModel(getModelClass(), sql, model);
	}

	@Override
	protected void afterDeleteModel(String deleteIds) throws Exception {
		clearOrgCache();
	}

	@Override
	protected void afterModify(M model) throws Exception {
		clearOrgCache();
	}

	@Override
	protected void afterSave(M model) throws Exception {
		clearOrgCache();
	}

	protected void clearOrgCache() {
		if (orgManager instanceof CacheableOrgManager) {
			((CacheableOrgManager) orgManager).clearCache();
		}
	}

}
