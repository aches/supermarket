package com.csm.common.routes;

import com.csm.action.main.AdminController;
import com.jfinal.config.Routes;



/**
 * 后台管理路由
 * @author zhiguo
 *
 */
public class AdminRoutes extends Routes {

	@Override
	public void config() {
		add("/admin", AdminController.class);
	}

}
