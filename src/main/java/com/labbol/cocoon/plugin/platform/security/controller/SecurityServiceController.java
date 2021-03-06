/**
 * 
 */
package com.labbol.cocoon.plugin.platform.security.controller;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.labbol.cocoon.msg.JsonMsg;
import com.labbol.core.controller.BaseCoreController;
import com.labbol.core.utils.SecurityUtils;

@Controller
public class SecurityServiceController extends BaseCoreController {

	@ResponseBody
	@RequestMapping(value = "user/securityService/operatePasswordValidate")
	public String operatePasswordValidate() {
		JsonMsg msg = new JsonMsg(false);
		Properties props = new Properties();
		try {
			String resourcePath = SecurityServiceController.class.getPackage().getName().replace(".", "/");
			props.load(SecurityUtils.decryptDES4InputStream(SecurityServiceController.class.getClassLoader()
					.getResourceAsStream(resourcePath + "/security-config.properties")));
			String serverOperPwd = props.getProperty("OPER_PWD");
			if ((StringUtils.isNotBlank(serverOperPwd))
					&& (serverOperPwd.equals(super.getRequest().getParameter("operPwd").toLowerCase()))) {
				msg.setSuccess(true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		msg.setMsg("操作码输入错误！");
		return toJson(msg);
	}

}
