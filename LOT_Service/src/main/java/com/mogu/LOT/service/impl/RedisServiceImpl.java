package com.mogu.LOT.service.impl;

import com.mogu.LOT.conf.JedisClusterFactory;
import com.mogu.LOT.service.RedisService;
import com.mogu.LOT.util.SerializableUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.JedisCluster;

import java.util.List;
import java.util.Set;

/**
 * Created by chang on 2017/6/19.
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private JedisClusterFactory jedisCluster;

//    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisCluster getJedisCluster(){
        if (null == jedisCluster)throw new NullPointerException();
        return jedisCluster.getJedisCluster();
    }
    @Override
    public <T> T getObj(String key, Class<T> type) {
        if (key != null) {
            try {
                byte[] _key = SerializableUtil.toByteArray(key);
                if (ArrayUtils.isEmpty(_key)) {
                    return null;
                }
                byte[] _value = getJedisCluster().get(_key);
                if (ArrayUtils.isEmpty(_value)) {
                    return null;
                }
                Object value = SerializableUtil.toObject(_value);
                return type.cast(value);
            } catch (ClassCastException e) {
                throw new IllegalStateException(e);
            }
        }
        return null;
    }


    @Override
    public String setObj(String key, Object val, int seconds) {
        try {
            byte[] _key = SerializableUtil.toByteArray(key);
            byte[] _val = SerializableUtil.toByteArray(val);
            return getJedisCluster().setex(_key, seconds, _val);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    @Override
    public String setString(String key, String value){
        return getJedisCluster().set(key,value);
    }


    @Override
    public Long del(String keys) {
        byte[] _key = SerializableUtil.toByteArray(keys);
        return getJedisCluster().del(_key);
    }

    @Override
    public Long delByString(String keys) {
        return getJedisCluster().del(keys);
    }


    /**
     * 向指定key 追加
     * @param key
     * @param str
     * @return key 值长度
     */
    @Override
    public Long append(String key, String str) {
          return   getJedisCluster().append(key,str);
    }


    @Override
    public Boolean exists(String key) {
        return getJedisCluster().exists(key);
    }


    /********************************************geo 地址坐标操作*********************/
    /**
     * geo 添加地理位置坐标
     * @param key
     * @param longitude
     * @param latitude
     * @param member
     */
    @Override
    public Long geoadd(String key, double longitude, double latitude, String member){
       return getJedisCluster().geoadd(key, longitude, latitude, member);
    }

    /**
     * 获取某个地理位置的geohash值
     * @param key
     * @param members
     * @return
     */
    @Override
    public List<String> geohash(String key, String... members){
        return getJedisCluster().geohash(key,members);
    }

    /**
     * 获取某个地理位置的坐标
     * @param key
     * @param members
     * @return
     */
    @Override
    public List<GeoCoordinate>  geopos(String key, String... members){
       return getJedisCluster().geopos(key,members);
    }

    /**
     * 获取两个地理位置的距离
     * @param key
     * @param member1
     * @param member2
     * @return
     */
    @Override
    public Double  geodist(String key, String member1, String member2){
       return getJedisCluster().geodist(key, member1, member2);
    }


    /**
     *根据给定地理位置坐标获取指定范围内的地理位置集合。
     * @param key
     * @param longitude
     * @param latitude
     * @param radius
     * @param unit
     * @return
     */
    @Override
    public List<GeoRadiusResponse>  georadius(String key, double longitude, double latitude, double radius, GeoUnit unit){
       return getJedisCluster().georadius(key, longitude, latitude, radius, unit);
    }

    /**
     * 根据给定地理位置获取指定范围内的地理位置集合
     * @param key
     * @param member
     * @param radius
     * @param unit
     * @return
     */
    @Override
    public List<GeoRadiusResponse>  georadiusbymember(String key, String member, double radius, GeoUnit unit){
        return getJedisCluster().georadiusByMember(key, member, radius, unit);
    }


    /************************************Set 集合操作*************************/
    /**
     * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
     * @param key
     * @param member
     * @return
     */
    @Override
    public Long sadd(String key, String... member){
        return getJedisCluster().sadd(key, member);
    }

    /**
     * 返回集合 key 的基数(集合中元素的数量)。
     * @param key
     * @return
     */
    @Override
    public Long scard(String key){
        return getJedisCluster().scard(key);
    }


    /**
     * 返回一个集合的全部成员，该集合是所有给定集合的交集。

     不存在的 key 被视为空集。

     当给定集合当中有一个空集时，结果也为空集(根据集合运算定律)。
     * @param keys
     * @return
     */
    @Override
    public Set<String> sinter(String... keys){
      return   getJedisCluster().sinter(keys);
    }

    /**
     * 所有
     * @param key
     * @return
     */
    @Override
    public Set<String> smembers(String key){
        return getJedisCluster().smembers(key);
    }


    @Override
    public Long srem(String key,String value){
        return getJedisCluster().srem(key,value);
    }

    @Override
    public Long zadd(String key, double score, String value){
        return getJedisCluster().zadd(key,score,value);
    }

    @Override
    public Set<String>  zrangeByScore(String key, double bg, double ed){
        return getJedisCluster().zrangeByScore(key,bg,ed);
    }

    @Override
    public boolean zrem(String key){
        return getJedisCluster().zrem(key)>0;
    }

    @Override
    public Set<String>  zrange(String key,long bg,long ed){
        return getJedisCluster().zrange(key, bg, ed);
    }


    public long  Zremrangebyscore(String key,long b,long e){return getJedisCluster().zremrangeByScore(key,b,e);}
}
