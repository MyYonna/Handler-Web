package org.handler.property.redis.persistence.entity;

import java.math.BigDecimal;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Association implements HttpSessionBindingListener{

	private final Logger log = LoggerFactory.getLogger(Association.class);
	private String associationName;
	private String associationAge;
	private BigDecimal associationIncome;
	public String getAssociationName() {
		return associationName;
	}
	public void setAssociationName(String associationName) {
		this.associationName = associationName;
	}
	public String getAssociationAge() {
		return associationAge;
	}
	public void setAssociationAge(String associationAge) {
		this.associationAge = associationAge;
	}
	public BigDecimal getAssociationIncome() {
		return associationIncome;
	}
	public void setAssociationIncome(BigDecimal associationIncome) {
		this.associationIncome = associationIncome;
	}
	@Override
	public void valueBound(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		log.info(arg0.getName()+"被session中添加，其值为："+arg0.getValue().toString());
	}
	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		log.info(arg0.getName()+"被从session中移除"+arg0.getValue().toString());
	}
	
	public String toString(){
		return this.getAssociationName();
	}
}
