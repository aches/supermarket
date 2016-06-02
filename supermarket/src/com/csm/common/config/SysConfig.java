package com.csm.common.config;

import com.csm.common.model._MappingKit;
import com.csm.common.routes.AdminRoutes;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.quartz.QuartzPlugin;
import com.jfinal.render.ViewType;

/**
 * API引导式配置
 */
public class SysConfig extends JFinalConfig {
	
	/**
	 * 如果生产环境配置文件存在，则优先加载该配置，否则加载开发环境配置文件
	 * @param pro 生产环境配置文件
	 * @param dev 开发环境配置文件
	 */
	public void loadProp(String pro, String dev) {
		try {
			PropKit.use(pro);
		}
		catch (Exception e) {
			PropKit.use(dev);
		}
	}
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		loadProp("a_little_config_pro.txt", "a_little_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", true));
		me.setEncoding("utf-8");
		me.setViewType(ViewType.JSP);
		me.setMaxPostSize(1024*1024*20);
		// ApiConfigKit 设为开发模式可以在开发阶段输出请求交互的 xml 与 json 数据
		
	}
	

	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		
		/** 微信接口路由 **/
		/** 后台管理路由 **/
		me.add(new AdminRoutes());

		
	}
	
	public static C3p0Plugin createC3p0Plugin() {
		return new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin C3p0Plugin = createC3p0Plugin();
		me.add(C3p0Plugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(C3p0Plugin);
		me.add(arp);
		 
		//缓存信息
		//EhCachePlugin ecp = new EhCachePlugin();
		// me.add(ecp);
		
		// 所有配置在 MappingKit 中搞定
		_MappingKit.mapping(arp);
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}
	
	@Override
	public void afterJFinalStart() {
		  QuartzPlugin plugin = new QuartzPlugin();
		  plugin.start();
		  Log.getLog(getClass()).info("----------------------job初始化成功--------------------");
		  super.afterJFinalStart();
	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		//org.slf4j.spi.LocationAwareLogger.log s = new org.slf4j.spi.LocationAwareLogger.log();
		JFinal.start("WebRoot", 8080, "/", 5);
	}
}
