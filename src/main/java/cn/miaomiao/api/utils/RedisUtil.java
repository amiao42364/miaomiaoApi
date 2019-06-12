package cn.miaomiao.api.utils;

import cn.miaomiao.api.constant.LogConstant;
import cn.miaomiao.api.constant.RedisConstant;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis工具类
 *
 * @author miaomiao
 * @date 2019/4/19 13:33
 */
@Component
@SuppressWarnings("unused")
@Slf4j
public class RedisUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * -------------------key相关操作---------------------
     * key存在时删除key
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 批量删除key
     */
    public void delete(Collection<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    /**
     * 检查key是否存在
     */
    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间
     */
    public Boolean expire(String key, long timeout) {
        return stringRedisTemplate.expire(key, RandomUtil.getRandomSecond(timeout), TimeUnit.SECONDS);
    }

    /**
     * 设置过期时间
     */
    public Boolean expireAt(String key, Date date) {
        return stringRedisTemplate.expireAt(key, date);
    }

    /**
     * 查找所有符合给定模式(pattern)的key
     */
    public Set<String> keys(String pattern) {
        return stringRedisTemplate.keys(pattern);
    }

    /**
     * 返回key的剩余的过期时间
     */
    public Long getExpire(String key, TimeUnit unit) {
        return stringRedisTemplate.getExpire(key, unit);
    }

    /**
     * 返回key的剩余的过期时间
     */
    public Long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    /**
     * 从当前数据库中随机返回一个key
     */
    public String randomKey() {
        return stringRedisTemplate.randomKey();
    }

    /**
     * -------------------string相关操作---------------------
     * 设置指定key的值
     */
    public void set(String key, String value) {
        set(key, value, RedisConstant.DEFAULT_TIMEOUT);
    }

    /**
     * 设置指定key的值
     */
    public void set(String key, String value, Long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, RandomUtil.getRandomSecond(timeout), TimeUnit.SECONDS);
    }

    /**
     * 获取指定key的值
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 增加(自增长), 负数则为自减
     */
    public Long incrBy(String key, long increment) {
        return stringRedisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 增加(自增长), 负数则为自减
     */
    public Double incrByFloat(String key, double increment) {
        return stringRedisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 获取对象
     */
    public <T> T getObject(String key, Class<T> clazz) {
        String value = get(key);
        try {
            return JSONObject.parseObject(value, clazz);
        } catch (Exception e) {
            log.error(LogConstant.REDIS_EXCEPTION + e.getMessage());
            return null;
        }
    }

    /**
     * 缓存对象
     */
    public void setObject(String key, Object obj) {
        setObject(key, obj, RedisConstant.DEFAULT_TIMEOUT);
    }

    /**
     * 缓存对象
     */
    public void setObject(String key, Object obj, Long timeout) {
        try {
            String value = JSONObject.toJSONString(obj);
            stringRedisTemplate.opsForValue().set(key, value, RandomUtil.getRandomSecond(timeout), TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(LogConstant.REDIS_EXCEPTION + e.getMessage());
        }
    }

    /**
     * -------------------hash相关操作-------------------------
     * 暂时只考虑Map<String, String>
     * 获取存储在哈希表中指定字段的值
     */
    public String hGet(String key, String field) {
        Object obj = stringRedisTemplate.opsForHash().get(key, field);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    /**
     * 获取指定key所有字段的值
     */
    public Map<String, String> hGetAll(String key) {
        Map<Object, Object> obj = stringRedisTemplate.opsForHash().entries(key);
        return obj.entrySet().stream().collect(Collectors.toMap(Object::toString, Object::toString));
    }

    public void hPut(String key, String hashKey, String value) {
        hPut(key, hashKey, value, RedisConstant.DEFAULT_TIMEOUT);
    }

    public void hPut(String key, String hashKey, String value, Long timeout) {
        stringRedisTemplate.opsForHash().put(key, hashKey, value);
        stringRedisTemplate.expire(key, RandomUtil.getRandomSecond(timeout), TimeUnit.SECONDS);
    }

    public void hPutAll(String key, Map<String, String> maps) {
        hPutAll(key, maps, RedisConstant.DEFAULT_TIMEOUT);
    }

    public void hPutAll(String key, Map<String, String> maps, Long timeout) {
        stringRedisTemplate.opsForHash().putAll(key, maps);
        stringRedisTemplate.expire(key, RandomUtil.getRandomSecond(timeout), TimeUnit.SECONDS);
    }

    /**
     * 删除一个或多个哈希表字段
     */
    public Long hDelete(String key, Object... fields) {
        if (fields.length < 1) {
            return null;
        }
        return stringRedisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     */
    public boolean hExists(String key, String field) {
        return stringRedisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     */
    public Long hIncrBy(String key, Object field, long increment) {
        return stringRedisTemplate.opsForHash().increment(key, field, increment);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     */
    public Double hIncrByFloat(String key, Object field, double delta) {
        return stringRedisTemplate.opsForHash().increment(key, field, delta);
    }

    /**
     * 获取所有哈希表中的字段
     */
    public Set<Object> hKeys(String key) {
        return stringRedisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取哈希表中字段的数量
     */
    public Long hSize(String key) {
        return stringRedisTemplate.opsForHash().size(key);
    }

    /**
     * 获取哈希表中所有值
     */
    public List<Object> hValues(String key) {
        return stringRedisTemplate.opsForHash().values(key);
    }

    /**
     * ------------------------list相关操作----------------------------
     * list相关add操作默认放在最后
     * 获取列表中的元素
     */
    public List<String> lGet(String key) {
        return stringRedisTemplate.opsForList().range(key, 0, -1);
    }

    public <T> List<T> lGetObject(String key, Class<T> clazz) {
        List<String> list = stringRedisTemplate.opsForList().range(key, 0, -1);
        if (list == null || list.isEmpty()) {
            return null;
        }
        List<T> result;
        try {
            result = list.stream().map(a -> JSONObject.parseObject(a, clazz)).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(LogConstant.REDIS_EXCEPTION + e.getMessage());
            return null;
        }
        return result;
    }

    public <T> T lPop(String key, Class<T> clazz){
        String object = stringRedisTemplate.opsForList().leftPop(key);
        try {
            return JSONObject.parseObject(object, clazz);
        } catch (Exception e) {
            log.error(LogConstant.REDIS_EXCEPTION + e.getMessage());
            return null;
        }
    }

    /**
     * 写入集合
     */
    public <T> Long lSet(String key, List<T> value) {
        return lSet(key, value, RedisConstant.DEFAULT_TIMEOUT);
    }

    /**
     * 写入集合
     */
    public <T> Long lSet(String key, List<T> value, long timeout) {
        List<String> val;
        try {
            val = value.stream().map(JSONObject::toJSONString).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(LogConstant.REDIS_EXCEPTION + e.getMessage());
            return null;
        }
        Long result = stringRedisTemplate.opsForList().rightPushAll(key, val);
        stringRedisTemplate.expire(key, RandomUtil.getRandomSecond(timeout), TimeUnit.SECONDS);
        return result;
    }

    /**
     * 获取列表长度
     */
    public Long lLen(String key) {
        return stringRedisTemplate.opsForList().size(key);
    }

    /**
     * --------------------set相关操作--------------------------
     * set添加元素
     */
    public Long sAdd(String key, String... values) {
        return stringRedisTemplate.opsForSet().add(key, values);
    }

    /**
     * set移除元素
     */
    public Long sRemove(String key, Object... values) {
        return stringRedisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 获取集合的大小
     */
    public Long sSize(String key) {
        return stringRedisTemplate.opsForSet().size(key);
    }

    /**
     * 判断集合是否包含value
     */
    public Boolean sIsMember(String key, Object value) {
        return stringRedisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取集合所有元素
     */
    public Set<String> setMembers(String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }
}
