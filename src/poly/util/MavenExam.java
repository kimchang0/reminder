package poly.util;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class MavenExam {
	public static void main(String[] args) {
		
		String IP = "localhost";
		int PORT = 6379;
		int TIME_OUT = 1000;
		String PASSWORD = "password";
		
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		
		JedisPool pool = new JedisPool(jedisPoolConfig , IP , PORT , TIME_OUT , PASSWORD );
		
		Jedis jedis = pool.getResource();
		
		jedis.set("NAME", "YUNJIN");
		
		System.out.println(jedis.get("NAME"));
		
		
		if (jedis != null) {
			jedis.close();
			jedis = null;
		}
		pool.close();
	}
}
