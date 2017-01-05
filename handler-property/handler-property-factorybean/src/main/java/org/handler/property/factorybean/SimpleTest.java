package org.handler.property.factorybean;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SuppressWarnings("deprecation")
public class SimpleTest {
	public static void main(String[] args){
        Resource res = new ClassPathResource("spring/applicationContext.xml");  
        BeanFactory factory = new XmlBeanFactory(res);  
        System.out.println(factory.getBean("factoryBeanOne").getClass());  
        System.out.println(factory.getBean("factoryBeanTwo").getClass());  
}
}
