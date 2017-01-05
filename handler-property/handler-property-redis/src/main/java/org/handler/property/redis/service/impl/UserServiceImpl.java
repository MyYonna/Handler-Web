package org.handler.property.redis.service.impl;

import java.util.List;

import org.handler.property.redis.persistence.dao.TabDeveloperDao;
import org.handler.property.redis.persistence.dao.UserDao;
import org.handler.property.redis.persistence.entity.TabDeveloper;
import org.handler.property.redis.persistence.entity.User;
import org.handler.property.redis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private TabDeveloperDao tabDeveloperDao;
	
	private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public String addUser(User user) {
		// TODO Auto-generated method stub
		userDao.addUser(user);
//		User u = userDao.getUser();
		List<User> list = userDao.getUserList();
		System.out.println(list.size()+".......................................个");
		return user.getId();
	}
	
	public void updateUser(User user){
		userDao.updateUser(user);
	}

	public String addDeveloper(TabDeveloper td){
		log.info("add Developer into DB............................");
		int i = tabDeveloperDao.insert(td);
		if(i>1){
			return td.getId();
		}
		return "";
	}

	@Override
	@Cacheable(value="default")
	public TabDeveloper getDeveloperById(String id) {
		// TODO Auto-generated method stub
		log.info("get data from DB..................");
		return tabDeveloperDao.selectByPrimaryKey(id);
	}
	
	@CacheEvict(value="default" ,key="#td.getId()")
	public void updateDeveloper(TabDeveloper td){
		log.info("update Developer into DB............................");
		tabDeveloperDao.updateByPrimaryKeySelective(td);
	}

	@Override
	@CacheEvict(value="default",allEntries=true)
	public void reloadCache() {
		// TODO Auto-generated method stub
		log.info("清空缓存。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
	}
	
}
