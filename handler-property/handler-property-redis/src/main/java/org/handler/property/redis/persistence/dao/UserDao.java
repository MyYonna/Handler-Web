package org.handler.property.redis.persistence.dao;

import java.util.List;

import org.handler.property.redis.common.RedisBaseDao;
import org.handler.property.redis.persistence.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component(value="userDao")
public class UserDao extends RedisBaseDao<User>{

	public String addUser(User user){
		this.lpushObject(user);
		return user.getId();
	}
	
	public User getUser(){
		User user = (User)this.lgetObject(User.class.getName(), 0);
		return user;
	}
	@Cacheable("default")
	public List<User> getUserList(){
		List<User> list =this.lgetList(User.class.getName());
		return list;
	}
	@Cacheable("default")
	public void updateUser(User user){
		
	}
}
