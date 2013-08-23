import redis.clients.jedis.*

JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost")

return pool
