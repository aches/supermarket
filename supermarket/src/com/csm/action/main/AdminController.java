package com.csm.action.main;

import com.jfinal.core.Controller;

/**
 * MediaController
 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class AdminController extends Controller {
	public void index() {
		System.out.println(123);
		renderJson("key", "test");
	}
	
	
}


