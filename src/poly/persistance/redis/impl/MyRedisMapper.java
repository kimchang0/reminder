package poly.persistance.redis.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import poly.persistance.redis.IMyRedisMapper;

@Component("MyRedisMapper")
// 스프링을 통해 싱글톤으로 Mapper를 메모리에 로딩하기 위해 사용 
public class MyRedisMapper implements IMyRedisMapper {
	
	@Autowired
	public RedisTemplate<String, Object> redisDB;
	// Context-redis.xml 파일에 정의된 redis객체
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public void doSaveData() throws Exception {
		
		log.info(this.getClass().getName() + ".getCacheData service Start!!");
		
		String redisKey = "Test01";
		String saveData = "난 저장되는 데이터";
		
		/*
		 * redis 저장 및 읽기에 대한 데이터 타입 지정 (String 타입으로 지정함)
		 */
		redisDB.setKeySerializer(new StringRedisSerializer());	// String type
		redisDB.setValueSerializer(new StringRedisSerializer());// String type
		
		/*
		 * 2 데이터가 존재하면 바로 반환
		 */
		
		// 키가 존재하면 데이터 읽기 
		if (redisDB.hasKey(redisKey)) {
			String res = (String) redisDB.opsForValue().get(redisKey);
			
			log.info("res: " + res);
		// 키가 없으면 데이터 저장 
		} else {
			redisDB.opsForValue().set(redisKey, saveData);
			
			log.info("No DATA!!");
		}
		
	}
}
