package com.mogu.LOT.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;

import java.util.List;
import java.util.Set;

/**
 * Created by chang on 2017/6/19.
 */
public interface RedisService {
    <T> T getObj(String key, Class<T> type);

    String setObj(String key, Object val, int seconds);

    String setString(String key, String value);

    Long del(String keys);

    Long delByString(String keys);

    Long append(String key, String str);

    Boolean exists(String key);

    Long geoadd(String key, double longitude, double latitude, String member);

    List<String> geohash(String key, String... members);

    List<GeoCoordinate>  geopos(String key, String... members);

    Double  geodist(String key, String member1, String member2);

    List<GeoRadiusResponse>  georadius(String key, double longitude, double latitude, double radius, GeoUnit unit);

    List<GeoRadiusResponse>  georadiusbymember(String key, String member, double radius, GeoUnit unit);

    Long sadd(String key, String... member);

    Long scard(String key);

    Set<String> sinter(String... keys);

    Set<String> smembers(String key);

    Long srem(String key, String value);

    Long zadd(String key, double score, String value);

    Set<String>  zrangeByScore(String key, double bg, double ed);

    boolean zrem(String key);

    Set<String> zrange(String key,long bg,long ed);

    long Zremrangebyscore(String key,long b,long e);
}
