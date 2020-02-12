package com.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class RedisPool {

  private JedisPoolConfig config;

  @Value("${spring.redis.host}")
  private String          serverIp;

  @Value("${spring.redis.port}")
  private int             port;

  @Value("${spring.redis.password}")
  private String          password;

  private JedisPool       pool;

  public void init() {
    config = new JedisPoolConfig();
    pool = new JedisPool(config, serverIp, port, 4000, password);
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Jedis getInstance() {
    return pool.getResource();
  }

  public void returnResource(Jedis jedis) {
    pool.returnResource(jedis);
  }

  public void returnBrokenResource(Jedis jedis) {
    pool.returnBrokenResource(jedis);
  }

  public JedisPoolConfig getConfig() {
    return config;
  }

  public void setConfig(JedisPoolConfig config) {
    this.config = config;
  }

  public String getServerIp() {
    return serverIp;
  }

  public void setServerIp(String serverIp) {
    this.serverIp = serverIp;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

}
