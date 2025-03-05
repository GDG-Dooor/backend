package com.example.dooor.ranking.application;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component 
public class RedisRankingService { // redis db 설정

    private final JedisPool jedisPool;

    public RedisRankingService() {
        this.jedisPool = new JedisPool("localhost", 6379); // Redis 서버 주소
    }

    public void updateRanking(Integer userId, Integer score) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.zadd("rankings", score, userId.toString()); // 점수 추가
        }
    }

    public List<String> getTopRankings(int topN) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrange("rankings", 0, topN - 1); // 상위 N명 조회
        }
    }
}

