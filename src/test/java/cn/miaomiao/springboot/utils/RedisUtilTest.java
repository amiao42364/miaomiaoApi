package cn.miaomiao.springboot.utils;

import cn.miaomiao.springboot.entity.User;
import com.alibaba.fastjson.JSONObject;
import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author miaomiao
 * @date 2019/4/19 14:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilTest {

    @Resource
    private RedisUtil redisUtil;

    private static User user = new User(UUID.getInstance().id(), "miaomiao", 1, "miaomiaomiao");


    @Test
    public void commonTest() {
        String key = "aaa";
        String value = "bbb";
        redisUtil.set(key, value);
        String result = redisUtil.get(key);
        Assert.assertEquals(value, result);
        redisUtil.delete(key);
        Assert.assertEquals(redisUtil.hasKey(key), false);
    }

    @Test
    public void objTest() {
        String key = "a1a1";
        redisUtil.setObject(key, user);
        User newUser = redisUtil.getObject(key, User.class);
        Assert.assertEquals(newUser.getName(), user.getName());

    }

    @Test
    public void hashTest() {
        redisUtil.hPut("h1h1", "num1", "val1");
        redisUtil.hPut("h1h1", "num2", "val2");
        redisUtil.hPut("h1h1", "num1", "1111");
        redisUtil.hPut("h1h1", "user", JSONObject.toJSONString(user));
        redisUtil.hPut("h2h2", "num1", "1111");
        redisUtil.hPut("h2h2", "h2h2", "1111");
        String value1 = redisUtil.hGet("h1h1", "num1");
        Assert.assertEquals(value1, "1111");
        String value2 = redisUtil.hGet("h1h1", "num2");
        Assert.assertEquals(value2, "val2");
        String value3 = redisUtil.hGet("h1h1", "user");
        User newUser = JSONObject.parseObject(value3, User.class);
        Assert.assertEquals(newUser.getName(), user.getName());
        Assert.assertEquals(newUser.getDesc(), user.getDesc());
        Map<String, String> map = redisUtil.hGetAll("h1h1");
        Assert.assertEquals(map.size(), 3);

        redisUtil.hDelete("h1h1", "num1", "num2");
        map = redisUtil.hGetAll("h1h1");
        Assert.assertEquals(map.size(), 1);

    }
}
