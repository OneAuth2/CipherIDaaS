package com.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisClient<K, V> {






    @Autowired
    private RedisPool redisPool;

    @Autowired
    private RedisTemplate<K, V> redisTemplate;

    private final long DEFAULT_EXPIRE_TIME = 1 * 24 * 60 * 60;

    @Autowired
    private KryoRedisSerializer<Object> kryoSerializer;

    /**
     * 放入缓存服务器，默认存活24小时
     *
     * @param key   缓存key值
     * @param value 缓存value值(object对象)
     */
    public void put(K key, V value) {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps(key);
        valueOper.set(value);
        redisTemplate.expire(key, DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    /**
     * 放入缓存服务器
     *
     * @param key   缓存key值
     * @param value 缓存value值(object对象)
     * @param ttl   缓存有效时间,单位秒,-1表示永久缓存
     */
    public void put(K key, V value, long ttl) {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps(key);
        valueOper.set(value);
        if (ttl > 0) {
            redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
        }
    }

    /**
     * 保存对象对缓存服务器
     *
     * @param key         缓存key
     * @param value       缓存值
     * @param expiredDate 缓存截止日期
     */
    public void put(K key, V value, Date expiredDate) {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps(key);
        valueOper.set(value);
        redisTemplate.expireAt(key, expiredDate);
    }

    /**
     * 获取缓存服务器存储结果值
     *
     * @param key 缓存key值
     * @return 返回结果对象
     */
    public Object get(K key) {
        BoundValueOperations<K, V> boundValueOper = redisTemplate.boundValueOps(key);
        if (boundValueOper == null) {
            return null;
        }
        return boundValueOper.get();
    }

    /**
     * 保存List类型数据,存活时间为24小时
     *
     * @param key  存储key
     * @param list 存储list值
     */
    @SuppressWarnings("unchecked")
    public void putList(final K key, final V list) {
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(@SuppressWarnings("rawtypes") RedisOperations operations) throws DataAccessException {
                operations.watch(rawKey(key));
                operations.multi();
                operations.delete(key);
                BoundListOperations<K, V> listOper = operations.boundListOps(key);
                listOper.leftPush(list);
                operations.expire(key, DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
                operations.exec();
                return null;
            }
        });

    }

    /**
     * 保存list类型数据
     *
     * @param key  存储key
     * @param list 存储list值
     * @param ttl  存活时间,单位秒
     */
    @SuppressWarnings("unchecked")
    public void putList(final K key, final V list, final long ttl) {

        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(@SuppressWarnings("rawtypes") RedisOperations operations) throws DataAccessException {
                operations.watch(rawKey(key));
                operations.multi();
                operations.delete(key);
                BoundListOperations<K, V> listOper = operations.boundListOps(key);
                listOper.leftPush(list);
                operations.expire(key, ttl, TimeUnit.SECONDS);
                operations.exec();
                return null;
            }
        });
    }

    /**
     * 保存list类型数据
     *
     * @param key         存储key
     * @param list        存储list值
     * @param expiredDate 存活截止日期
     */
    public void putList(final K key, final V list, final Date expiredDate) {

        redisTemplate.execute(new SessionCallback<Object>() {
            @SuppressWarnings("unchecked")
            @Override
            public Object execute(@SuppressWarnings("rawtypes") RedisOperations operations) throws DataAccessException {
                operations.watch(rawKey(key));
                operations.multi();
                operations.delete(key);
                BoundListOperations<K, V> listOper = operations.boundListOps(key);
                listOper.leftPush(list);
                operations.expireAt(key, expiredDate);
                operations.exec();
                return null;
            }
        });
    }

    /**
     * 获取List值
     *
     * @param key
     * @return 获取结果为List类型的值
     */
    public List<? extends Object> getList(K key) {
        BoundListOperations<K, V> listOper = redisTemplate.boundListOps(key);
        if (listOper == null) {
            return null;
        }
        return listOper.range(0, -1);
    }

    public void remove(K key) {
        redisTemplate.delete(key);
    }

    public void remove(Collection<K> ks) {
        redisTemplate.delete(ks);
    }

    public long getExpire(K key) {
        return redisTemplate.getExpire(key);
    }

    public boolean containsKey(K key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 保存Set类型数据
     *
     * @param key   存储key
     * @param value 存储值
     * @param ttl   存活时间,单位秒
     */
    @SuppressWarnings("unchecked")
    public void putSet(final K key, final V value, final long ttl) {
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(@SuppressWarnings("rawtypes") RedisOperations operations) throws DataAccessException {
                operations.watch(rawKey(key));
                operations.multi();
                operations.delete(key);
                BoundSetOperations<K, V> setOper = operations.boundSetOps(key);
                setOper.add(value);
                operations.expire(key, ttl, TimeUnit.SECONDS);
                operations.exec();
                return null;
            }
        });
    }

    /**
     * 保存Set类型数据
     *
     * @param key         存储key
     * @param value         存储值
     * @param expiredDate 存活截止日期
     */
    @SuppressWarnings("unchecked")
    public void putSet(final K key, final V value, final Date expiredDate) {

        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(@SuppressWarnings("rawtypes") RedisOperations operations) throws DataAccessException {
                operations.watch(rawKey(key));
                operations.multi();
                operations.delete(key);
                BoundSetOperations<K, V> setOper = operations.boundSetOps(key);
                setOper.add(value);
                operations.expireAt(key, expiredDate);
                operations.exec();
                return null;
            }
        });
    }

    /**
     * 保存set类型的值，默认有效期1天
     *
     * @param key
     * @param value
     */
    @SuppressWarnings("unchecked")
    public void putSet(final K key, final V value) {

        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(@SuppressWarnings("rawtypes") RedisOperations operations) throws DataAccessException {
                operations.watch(rawKey(key));
                operations.multi();
                operations.delete(key);
                BoundSetOperations<K, V> setOper = operations.boundSetOps(key);
                setOper.add(value);
                operations.expire(key, DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
                operations.exec();
                return null;
            }
        });
    }

    /**
     * 获取Set类型值
     *
     * @param key
     * @return
     */
    public Set<V> getSet(K key) {
        BoundSetOperations<K, V> setOper = redisTemplate.boundSetOps(key);
        return setOper.members();
    }

    public void putHash(final K key, final Map<? extends V, ? extends Object> value) {
        redisTemplate.execute(new SessionCallback<Object>() {
            @SuppressWarnings("unchecked")
            @Override
            public Object execute(@SuppressWarnings("rawtypes") RedisOperations operations) throws DataAccessException {
                operations.watch(rawKey(key));
                operations.multi();
                operations.delete(key);
                BoundHashOperations<K, Object, Object> hashOper = redisTemplate.boundHashOps(key);
                hashOper.putAll(value);
                operations.expire(key, DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
                operations.exec();
                return null;
            }
        });
    }

    /**
     * 获取hash值
     *
     * @param key
     * @return 获取结果为hash类型的值
     */
    public Map<Object, Object> getHash(K key) {
        BoundHashOperations<K, Object, Object> hashOper = redisTemplate.boundHashOps(key);
        if (hashOper == null) {
            return null;
        }
        return hashOper.entries();
    }

    public void kset(final K key, V value, final long ttl) {
        final byte[] bytes = kryoSerializer.serialize(value);
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(rawKey(key), ttl, bytes);
                return null;
            }
        });
    }

    public void kset(final K key, V value) {
        final byte[] bytes = kryoSerializer.serialize(value);
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(rawKey(key), DEFAULT_EXPIRE_TIME, bytes);
                return null;
            }
        });
    }

    public Object kget(final K key) {
        Object result = redisTemplate.execute(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] bytes = connection.get(rawKey(key));
                if (bytes == null) {
                    return null;
                }
                return kryoSerializer.deserialize(bytes);
            }
        });
        return result;
    }

    public long incrementBy(final K key, final long incrBy, final long ttl) {
        Object obj = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                long ret = connection.incrBy(rawKey(key), incrBy);
                connection.expire(rawKey(key), ttl);
                return ret;
            }
        });
        return (Long) obj;
    }

    public long decrement(final K key, final long ttl) {
        Object obj = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                long ret = connection.decr(rawKey(key));
                connection.expire(rawKey(key), ttl);
                return ret;
            }
        });
        return (Long) obj;
    }

    private byte[] rawKey(K key) {
        return key.toString().getBytes();
    }

    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setKryoSerializer(KryoRedisSerializer<Object> kryoSerializer) {
        this.kryoSerializer = kryoSerializer;
    }

    public String flushDB() {
        redisPool.init();
        Jedis jedis = getJedis();
        String result = jedis.flushDB();
        releaseJedis(jedis);
        return result;
    }


    public Jedis getJedis() {
        return redisPool.getInstance();
    }

    public void releaseJedis(Jedis jedis) {
        redisPool.returnResource(jedis);
    }

}
