package org.handler.property.redis.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyServletContextAttributteListner implements ServletContextAttributeListener {

	private final Logger log =  LoggerFactory.getLogger(MyServletContextAttributteListner.class);
	@Override
	public void attributeAdded(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub
		String name = arg0.getName();
		Object value = arg0.getValue();
		log.info("Context  "+name+"属性被添加进来，属性值为："+value.toString());
		
	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub

		String name = arg0.getName();
		Object value = arg0.getValue();
		log.info("Context  "+name+"属性被移除出去，属性值为："+value.toString());
		
	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub
		String name = arg0.getName();
		Object value = arg0.getValue();
		log.info("Context  "+name+"属性被替换，属性值为："+value.toString());
		
	}

}
