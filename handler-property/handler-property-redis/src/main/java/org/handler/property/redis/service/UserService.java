package org.handler.property.redis.service;

import org.handler.property.redis.persistence.entity.TabDeveloper;
import org.handler.property.redis.persistence.entity.User;

public interface UserService {

	public String addUser(User user);
	
	public void updateUser(User user);
	
	public String addDeveloper(TabDeveloper td);
	
	public TabDeveloper getDeveloperById(String id);
	
	public void updateDeveloper(TabDeveloper td);
	
	public void reloadCache();
}
