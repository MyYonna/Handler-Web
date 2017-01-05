package org.handler.property.factorybean;

import java.util.Date;

import org.springframework.beans.factory.FactoryBean;

@SuppressWarnings("rawtypes")
public class SimpleFactoryBean implements FactoryBean{

	private boolean flag;
	
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Object getObject() throws Exception {
		// TODO Auto-generated method stub
		if(flag){
			return new Date();
		}
		return new String("111");
	}

	public Class getObjectType() {
		// TODO Auto-generated method stub
		return flag?Date.class : String.class;
	}

	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}
	


}
