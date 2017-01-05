package org.handler.property.redis.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.transaction.annotation.Transactional;

public class RedisBaseDao<T>{

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Transactional
	public void lpush(final  String key,final  String value){
		redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection redisconnection) throws DataAccessException {
				// TODO Auto-generated method stub
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				redisconnection.setNX(serializer.serialize(key), serializer.serialize(value)); 
				return true;
			}});
	}
	
	public void lpushObject(final T obj){
		redisTemplate.execute(new RedisCallback<Boolean>(){

			@Override
			public Boolean doInRedis(RedisConnection redisconnection) throws DataAccessException {
				// TODO Auto-generated method stub
				@SuppressWarnings("unchecked")
				RedisSerializer<T> objSerializer = (RedisSerializer<T>)redisTemplate.getDefaultSerializer();
				RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
				byte[] keyByte = stringSerializer.serialize(obj.getClass().getName());
				byte[] objByte = objSerializer.serialize(obj);
				redisconnection.lPush(keyByte, objByte);
				return true;
			}
			
		});
	}
	
	public Object lgetObject( String key, int index){
				RedisConnection redisconnection = redisTemplate.getConnectionFactory().getConnection();
				// TODO Auto-generated method stub
				RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
				byte[] keyByte = stringSerializer.serialize(key);
				byte[] obj = redisconnection.lIndex(keyByte, index);
				@SuppressWarnings("unchecked")
				RedisSerializer<T> objSerializer = (RedisSerializer<T>)redisTemplate.getDefaultSerializer();
				T object = objSerializer.deserialize(obj);
				redisconnection.close();
				return object;
	}
	
	public List<T> lgetList( String key){
		RedisConnection redisconnection = redisTemplate.getConnectionFactory().getConnection();
		// TODO Auto-generated method stub
		RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
		byte[] keyByte = stringSerializer.serialize(key);
		List<byte[]> bList = redisconnection.lRange(keyByte, 0, -1);
		List<T> tList = this.deserializeList(bList);
		redisconnection.close();
		return tList;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> deserializeList(List<byte[]> bList){
		List<T> list = new ArrayList<T>();
		RedisSerializer<T> objSerializer = (RedisSerializer<T>)redisTemplate.getDefaultSerializer();
		for(int i=0;i<bList.size()-1;i++){
			byte[] b = bList.get(i);
			T t = objSerializer.deserialize(b);
			list.add(t);
		}
		return list;
	}
}
