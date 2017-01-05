package org.handler.property.redis.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class CommRedisCache implements Cache {
	private RedisTemplate<String, Object> redisTemplate;
	private String name;

	private byte[] getByteKey(Object key) {
		if ((key instanceof String)) {
			return key.toString().getBytes();
		}
		return redisTemplate.getStringSerializer().serialize(key.toString());
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return this.redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public Object getNativeCache() {
		return this.redisTemplate;
	}

	public Cache.ValueWrapper get(Object key) {
		String newKey = "yscDefaultKey";
		if (null != key) {
			newKey = key.toString();
		}

		final String keyf = newKey;

		Cache.ValueWrapper retObj = null;
		try {
			Object object = null;
			object = this.redisTemplate.execute(new RedisCallback() {
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					byte[] key = CommRedisCache.this.getByteKey(keyf);
					byte[] value = connection.get(key);
					if (value == null) {
						return null;
					}
					return CommRedisCache.this.toObject(value);
				}
			});
			if (null != object)
				retObj = new SimpleValueWrapper(object);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retObj;
	}

	public void put(Object key, Object value) {
		String newKey = "yscDefaultKey";
		if (null != key) {
			newKey = key.toString();
		}

		final String keyf = newKey;

		final Object valuef = value;
		long liveTimeSeconds = 86400L;
		try {
			this.redisTemplate.execute(new RedisCallback() {
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					byte[] keyb = CommRedisCache.this.getByteKey(keyf);
					byte[] valueb = CommRedisCache.this.toByteArray(valuef);
					connection.set(keyb, valueb);

					connection.expire(keyb, 86400L);

					return Long.valueOf(1L);
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private byte[] toByteArray(Object obj) {
		byte[] bytes = null;
		ObjectOutputStream oos = null;
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
				if (bos != null)
					bos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}

	private Object toObject(byte[] bytes) {
		Object obj = null;
		ObjectInputStream ois = null;
		ByteArrayInputStream bis = null;
		try {
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			obj = ois.readObject();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
				if (bis != null)
					bis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	public void evict(Object key) {
		String newKey = "yscDefaultKey";
		if (null != key) {
			newKey = key.toString();
		}

		final String keyf = newKey;
		try {
			this.redisTemplate.execute(new RedisCallback() {
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					return connection.del(new byte[][] { CommRedisCache.this.getByteKey(keyf) });
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void clear() {
		try {
			this.redisTemplate.execute(new RedisCallback() {
				public String doInRedis(RedisConnection connection) throws DataAccessException {
					connection.flushDb();
					return "ok";
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public <T> T get(Object arg0, Class<T> arg1) {
		return null;
	}
}
