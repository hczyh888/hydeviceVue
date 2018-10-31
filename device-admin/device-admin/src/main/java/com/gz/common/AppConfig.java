package com.gz.common;

import com.gz.common.model._MappingKit;
import com.gz.controller.DeviceController;
import com.gz.controller.FileToolController;
import com.gz.controller.IndexController;
import com.gz.controller.ProductController;
import com.gz.controller.SystemController;
import com.gz.controller.UserController;
import com.gz.controller.WarehouseController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log4jLog;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.template.Engine;

import hyevent.DuangBeanFactory;
import net.dreamlu.event.EventPlugin;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * API引导式配置
 */
public class AppConfig extends JFinalConfig {
	private Log4jLog log = Log4jLog.getLog(AppConfig.class);
	
	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 * 
	 * 使用本方法启动过第一次以后，会在开发工具的 debug、run config 中自动生成
	 * 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM argument 可配置为：
	 * -XX:PermSize=64M -XX:MaxPermSize=256M
	 */
	public static void main(String[] args) {
		/**
		 * 特别注意：Eclipse 之下建议的启动方式
		 */
		//JFinal.start("src/main/webapp", 80, "/", 5);
		
		/**
		 * 特别注意：IDEA 之下建议的启动方式，仅比 eclipse 之下少了最后一个参数
		 */
		 JFinal.start("device-admin/src/main/webapp", 80, "/");

	}
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("a_little_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		me.setBaseUploadPath(PropKit.get("FILE_UPLOAD_PATH"));
		me.setBaseDownloadPath(PropKit.get("FILE_UPLOAD_PATH"));
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		
		me.add("/", IndexController.class);
		me.add("/file", FileToolController.class);
		me.add("/product", ProductController.class);
		me.add("/user", UserController.class);
		me.add("/system",SystemController.class);
		me.add("/device",DeviceController.class);
		me.add("/warehouse",WarehouseController.class);
	}

	public void configEngine(Engine me) {

	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置 druid 数据库连接池插件
		DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
		me.add(druidPlugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		// 所有映射在 MappingKit 中自动化搞定
		_MappingKit.mapping(arp);
		arp.setShowSql(PropKit.getBoolean ("showSql",false));
		me.add(arp);
		//ehcache缓存
		me.add(new EhCachePlugin());
		//even plugin
		EventPlugin eventPlugin= new  EventPlugin();
		eventPlugin.async();
		eventPlugin.scanJar();
		eventPlugin.scanPackage("hyevent");
		eventPlugin.beanFactory(new DuangBeanFactory());
		me.add(eventPlugin);

	}
	
	public static DruidPlugin createDruidPlugin() {
		return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
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
		//me.add(new ResourceHandler());
	}
	@Override
	public void afterJFinalStart() {

		//检查基础资料
		//log.info("begin检查用户资料...");
		//AppStartDo.CheckUser();
		//log.info("end检查用户资料...");
		log.info("begin检查单据编号...");
		AppStartDo.CheckAllBillNo();
		log.info("end检查单据编号...");
	}
	@Override
	public void beforeJFinalStop(){
		log.info("回写单据编号到数据库...");
		AppStartDo.SaveAllBillNo(Constant.BILL_RKDH,CacheKit.get(Constant.BILL_CACHE,Constant.BILL_RKDH));


	}
 

}
