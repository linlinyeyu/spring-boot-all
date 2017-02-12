package com.ibenben.redis;

import java.util.concurrent.locks.ReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibenben.exception.MmsException;
import com.ibenben.util.SerializeUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class MybatisRedisCache implements Cache {

	private static Logger logger = LoggerFactory.getLogger(MybatisRedisCache.class);
	
	private static JedisPool pool;
	private final ReadWriteLock readWriteLock = new DummyReadWriteLock();
	private String id;

	public MybatisRedisCache(final String id){
		if (id == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>MybatisRedisCache:id=" + id);
		this.id = id;
		RedisConfig redisConfig;
		try {
			redisConfig = RedisConfigurationBuilder.getInstance().parseConfiguration();
			pool = new JedisPool(redisConfig, redisConfig.getHost(), redisConfig.getPort(),
					redisConfig.getConnectionTimeout(), redisConfig.getPassword(), redisConfig.getDatabase());
		} catch (MmsException e) {
			logger.error("初始化连接池错误" + e.getMessage());
			throw new RuntimeException("初始化连接池错误");
		}
	  }

	private Object execute(RedisCallback callback) {
		Jedis jedis = pool.getResource();
		try {
			return callback.doWithRedis(jedis);
		} finally {
			jedis.close();
		}
	}
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public int getSize() {
		return (Integer) execute(new RedisCallback() {
			@Override
			public Object doWithRedis(Jedis jedis) {
				return Integer.valueOf(jedis.dbSize().toString());
			}
		});
	}

	@Override
	public void putObject(Object key, Object value) {
		execute(new RedisCallback() {
			@Override
			public Object doWithRedis(Jedis jedis) {
				logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>putObject:" + key);
				return jedis.set(SerializeUtil.serialize(key.toString()), SerializeUtil.serialize(value));
			}
		});
	}

	@Override
	public Object getObject(Object key) {
		return execute(new RedisCallback() {
			@Override
			public Object doWithRedis(Jedis jedis) {
				Object value = SerializeUtil.unserialize(jedis.get(SerializeUtil.serialize(key.toString())));
				logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>getObject:" + key);
				return value;
			}
		});
	}

	@Override
	public Object removeObject(Object key) {
		return execute(new RedisCallback() {
			@Override
			public Object doWithRedis(Jedis jedis) {
				return jedis.expire(SerializeUtil.serialize(key.toString()), 0);
			}
		});
	}

	@Override
	public void clear() {
		execute(new RedisCallback() {
			@Override
			public Object doWithRedis(Jedis jedis) {
				jedis.flushDB();
				return null;
			}
		});
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}
	
}
