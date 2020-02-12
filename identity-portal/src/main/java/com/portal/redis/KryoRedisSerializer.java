package com.portal.redis;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.SoftReferenceObjectPool;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

@Component
public class KryoRedisSerializer<T> implements RedisSerializer<T> {
  private ObjectPool<Kryo> kryoFactory;

  @Override
  public byte[] serialize(Object t) throws SerializationException {
    byte[] buffer = new byte[2048];
    Output output = new Output(buffer, -1);
    getKryo().writeClassAndObject(output, t);
    return output.toBytes();
  }

  @Override
  public T deserialize(byte[] bytes) throws SerializationException {
    Input input = new Input(bytes);
    @SuppressWarnings("unchecked")
    T t = (T) getKryo().readClassAndObject(input);
    return t;
  }

  // 因为kryo是线程不安全的，所以这里用到对象池
  private Kryo getKryo() {
    if (this.kryoFactory == null) {
      this.kryoFactory = new SoftReferenceObjectPool<Kryo>(new PoolableKryoFactory());
    }
    try {
      return this.kryoFactory.borrowObject();
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }
}
