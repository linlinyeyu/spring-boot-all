package com.ibenben.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import com.ibenben.exception.MmsException;

final class RedisConfigurationBuilder {
	private static final RedisConfigurationBuilder INSTANCE = new RedisConfigurationBuilder();

	private static final String SYSTEM_PROPERTY_REDIS_PROPERTIES_FILENAME = "redis.properties.filename";

	private static final String REDIS_RESOURCE = "datasource.properties";

	private final String redisPropertiesFilename;

	/**
	 * Hidden constructor, this class can't be instantiated.
	 */
	private RedisConfigurationBuilder() {
		redisPropertiesFilename = System.getProperty(SYSTEM_PROPERTY_REDIS_PROPERTIES_FILENAME, REDIS_RESOURCE);
	}

	/**
	 * Return this class instance.
	 *
	 * @return this class instance.
	 */
	public static RedisConfigurationBuilder getInstance() {
		return INSTANCE;
	}

	/**
	 * Parses the Config and builds a new {@link RedisConfig}.
	 *
	 * @return the converted {@link RedisConfig}.
	 * @throws MmsException 
	 */
	public RedisConfig parseConfiguration() throws MmsException {
		return parseConfiguration(getClass().getClassLoader());
	}

	/**
	 * Parses the Config and builds a new {@link RedisConfig}.
	 *
	 * @param the
	 *            {@link ClassLoader} used to load the
	 *            {@code memcached.properties} file in classpath.
	 * @return the converted {@link RedisConfig}.
	 * @throws MmsException 
	 */
	public RedisConfig parseConfiguration(ClassLoader classLoader) throws MmsException {
		Properties config = new Properties();

		InputStream input = classLoader.getResourceAsStream(redisPropertiesFilename);
		if (input != null) {
			try {
				config.load(input);
			} catch (IOException e) {
				throw new RuntimeException(
						"An error occurred while reading classpath property '"
								+ redisPropertiesFilename
								+ "', see nested exceptions", e);
			} finally {
				try {
					input.close();
				} catch (IOException e) {
					// close quietly
				}
			}
		}

		RedisConfig jedisConfig = new RedisConfig();
		setConfigProperties(config, jedisConfig);
		return jedisConfig;
	}

	private void setConfigProperties(Properties properties,
			RedisConfig jedisConfig) throws MmsException {
		if (properties != null) {
			MetaObject metaCache = SystemMetaObject.forObject(jedisConfig);
			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				String name = (String) entry.getKey();
				if(name.contains("spring.redis")){
					name = name.substring(name.lastIndexOf(".") + 1, name.length());
				}
				String value = (String) entry.getValue();
				if (metaCache.hasSetter(name)) {
					Class<?> type = metaCache.getSetterType(name);
					if (String.class == type) {
						metaCache.setValue(name, value);
					} else if (int.class == type || Integer.class == type) {
						metaCache.setValue(name, Integer.valueOf(value));
					} else if (long.class == type || Long.class == type) {
						metaCache.setValue(name, Long.valueOf(value));
					} else if (short.class == type || Short.class == type) {
						metaCache.setValue(name, Short.valueOf(value));
					} else if (byte.class == type || Byte.class == type) {
						metaCache.setValue(name, Byte.valueOf(value));
					} else if (float.class == type || Float.class == type) {
						metaCache.setValue(name, Float.valueOf(value));
					} else if (boolean.class == type || Boolean.class == type) {
						metaCache.setValue(name, Boolean.valueOf(value));
					} else if (double.class == type || Double.class == type) {
						metaCache.setValue(name, Double.valueOf(value));
					} else {
						throw new MmsException("Unsupported property type: '"
								+ name + "' of type " + type);
					}
				}
			}
		}
	}
}
