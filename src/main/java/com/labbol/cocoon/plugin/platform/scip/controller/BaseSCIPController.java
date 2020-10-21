package com.labbol.cocoon.plugin.platform.scip.controller;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yelong.commons.lang.Strings;

import com.labbol.cocoon.controller.BaseCrudSupportController;
import com.labbol.cocoon.msg.JsonMsg;
import com.labbol.core.platform.scip.model.SCIP;
import com.labbol.core.support.ip.Ipv4;
import com.labbol.core.support.ip.Ipv4Utils;

@RequestMapping("sCIP")
public abstract class BaseSCIPController<M extends SCIP> extends BaseCrudSupportController<M> {

	@RequestMapping("index")
	public String index() {
		return "platform/scip/sCIPManage.jsp";
	}

	@Override
	protected void beforeQueryModel(SCIP model) {
		model.addConditionOperator("startIp", "LIKE");
	}

	@Override
	protected boolean validateModel(SCIP model, JsonMsg msg) {
		String startIp = model.getStartIp();
		Strings.requireNonBlank(startIp, "起始IP不能为空！");
		boolean lastStar = startIp.endsWith("*");
		if (lastStar ? !isValidIpv4AddrLastStar(startIp) : !isValidIpv4Addr(startIp)) {
			msg.setMsg(startIp + "不是标准的IPV4格式！");
			return false;
		}
		String endIp = model.getEndIp();
		if (StringUtils.isNotBlank(endIp)) {
			if (!isValidIpv4Addr(endIp)) {
				msg.setMsg(endIp + "不是标准的IPV4格式！");
				return false;
			}
			if (!Ipv4Utils.validateFirstThreeSection(startIp, endIp)) {
				msg.setMsg("起始IP与结束IP的前三个段必须相同！");
				return false;
			}
			Ipv4 startIpv4 = new Ipv4(startIp);
			Ipv4 endIpv4 = new Ipv4(endIp);
			if (endIpv4.getFourSection() < startIpv4.getFourSection()) {
				msg.setMsg("结束IP必须大于起始IP！");
				return false;
			}
		}
		return true;
	}

	public static boolean isValidIpv4Addr(String ipAddr) {
		if (StringUtils.isBlank(ipAddr)) {
			return false;
		}
		String regex = "(^((22[0-3]|2[0-1][0-9]|[0-1][0-9][0-9]|([0-9]){1,2})"
				+ "([.](25[0-5]|2[0-4][0-9]|[0-1][0-9][0-9]|([0-9]){1,2})){3})$)";
		ipAddr = Normalizer.normalize(ipAddr, Form.NFKC);
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(ipAddr);
		return matcher.matches();
	}

	public static boolean isValidIpv4AddrLastStar(String ipAddr) {
		if (StringUtils.isBlank(ipAddr)) {
			return false;
		}
		String regex = "(^((22[0-3]|2[0-1][0-9]|[0-1][0-9][0-9]|([0-9]){1,2})"
				+ "([.](25[0-5]|2[0-4][0-9]|[0-1][0-9][0-9]|([0-9]){1,2})){2})[.][*]$)";
		ipAddr = Normalizer.normalize(ipAddr, Form.NFKC);
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(ipAddr);
		return matcher.matches();
	}

}
