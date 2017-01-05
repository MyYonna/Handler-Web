package org.handler.property.redis.listener;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyServletRequestAttributeListner implements ServletRequestAttributeListener {

	private final Logger log =  LoggerFactory.getLogger(MyServletRequestAttributeListner.class);
	@Override
	public void attributeAdded(ServletRequestAttributeEvent arg0) {
		// TODO Auto-generated method stub
		String name = arg0.getName();
		Object value = arg0.getValue();
		log.info("Request  "+name+"属性被添加进来，属性值为："+value.toString());
	}

	@Override
	public void attributeRemoved(ServletRequestAttributeEvent arg0) {
		// TODO Auto-generated method stub
		String name = arg0.getName();
		Object value = arg0.getValue();
		log.info("Request  "+name+"属性被移除，属性值为："+value.toString());
	}

	@Override
	public void attributeReplaced(ServletRequestAttributeEvent arg0) {
		// TODO Auto-generated method stub
		String name = arg0.getName();
		Object value = arg0.getValue();
		log.info("Request  "+name+"属性被替换，属性值为："+value.toString());
	}

}
