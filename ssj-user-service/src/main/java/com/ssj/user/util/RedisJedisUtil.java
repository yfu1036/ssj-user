package com.ssj.user.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.Map;

@Component
public class RedisJedisUtil {

    @Autowired
    private JedisPool jedisPool;

    private final String DIST_LOCK_SUCCESS = "OK";
    private final Long DIST_LOCK_RELEASE_SUCCESS = 1L;
    private final String SET_IF_NOT_EXIST = "NX";
    private final String SET_WITH_EXPIRE_TIME = "PX";
    /**
     * 获取key对应字符串
     * @param key
     * @return
     */
    public String getString(String key){
        Jedis jedis = jedisPool.getResource();
        try {
            String value = jedis.get(key);
            return value;
        } catch (Exception e){
            return null;
        } finally {
            close(jedis);
        }
    }

    /**
     * 添加一个字符串值
     * @param key
     * @param value
     * @return
     */
    public boolean setString(String key, String value){
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.set(key, value);
            return true;
        } catch (Exception e){
            return false;
        } finally {
            close(jedis);
        }
    }

    public boolean setStringEx(String key, int seconds, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.setex(key, seconds, value);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            close(jedis);
        }
    }

    public boolean delKey(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.del(key);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            close(jedis);
        }
    }

    public boolean expireKey(String key, int seconds) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.expire(key, seconds);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            close(jedis);
        }
    }

    public boolean setHash(String key, String mKey, String mVal) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.hset(key, mKey, mVal);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            close(jedis);
        }
    }

    public boolean delHash(String key, String mKey) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.hdel(key, mKey);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            close(jedis);
        }
    }

    public String getHashString(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.hget(key, field);
        } catch (Exception e) {
            return null;
        } finally {
            close(jedis);
        }
    }

    public Map<String, String> getHashAll(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.hgetAll(key);
        } catch (Exception e) {
            return null;
        } finally {
            close(jedis);
        }
    }

    public Long lpush(String key, String... value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.lpush(key, value);
        } catch (Exception e) {
            return null;
        } finally {
            close(jedis);
        }
    }

    public String lpop(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.lpop(key);
        } catch (Exception e) {
            return null;
        } finally {
            close(jedis);
        }
    }

    public Long rpush(String key, String... value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.rpush(key, value);
        } catch (Exception e) {
            return null;
        } finally {
            close(jedis);
        }
    }

    public String rpop(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.rpop(key);
        } catch (Exception e) {
            return null;
        } finally {
            close(jedis);
        }
    }

    public boolean addScoreSet(String key, String mKey, int score) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.zadd(key, score, mKey);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            close(jedis);
        }
    }

    public boolean delScoreSet(String key, String mKey) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.zrem(key, mKey);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            close(jedis);
        }
    }

    /**
     * 加分布式锁
     * @param lockKey
     * @param requestId
     * @param expireTime
     * @return
     */
    public boolean getDistributedLock(String lockKey, String requestId, int expireTime) {
        Jedis jedis = jedisPool.getResource();
        try {
            String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            if (DIST_LOCK_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            close(jedis);
        }
    }

    /**
     * 释放分布式锁
     * @param lockKey
     * @param requestId
     * @return
     */
    public boolean releaseDistributedLock(String lockKey, String requestId) {
        Jedis jedis = jedisPool.getResource();
        try {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
            if (DIST_LOCK_RELEASE_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            close(jedis);
        }
    }

    /**
     * 关闭jedis实例
     * @param jedis
     */
    private void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
