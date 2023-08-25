package kr.or.ddit.basic;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;

public class MyServletContextListener implements ServletContextListener
		, ServletRequestAttributeListener{

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("[MyServletContextListener]"
				+ "contextDestroyed 호출됨");
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("[MyServletContextListener]"
				+ "contextInitialized 호출됨");
		
	}

	@Override
	public void attributeAdded(ServletRequestAttributeEvent event) {
		System.out.println("[MyServletContextListener]"
				+ "attributeAdded 호출됨 => "
				+ event.getName() + " : " + event.getValue());
		
	}

	@Override
	public void attributeRemoved(ServletRequestAttributeEvent event) {
		System.out.println("[MyServletContextListener]"
				+ "attributeRemoved 호출됨 => "
				+ event.getName() + " : " + event.getValue());
		
	}

	@Override
	public void attributeReplaced(ServletRequestAttributeEvent event) {
		System.out.println("[MyServletContextListener]"
				+ "attributeReplaced 호출됨 => "
				+ event.getName() + " : " + event.getValue());
		
	}

}
