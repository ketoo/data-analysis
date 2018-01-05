package com.nf.nosqlservice.impl;

import com.nf.nosqlservice.NFINosqlModule;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.toIntExact;

/**
 * Created by lushenghuang on 20/12/17.
 */

@Service
public class NFNosqlModule implements NFINosqlModule
{
    Logger logger = Logger.getLogger(this.getClass().getName());

    private JedisPool pool;

    @Value("${redis.server}")
    private String redisServer;

    @Value("${redis.auth}")
    private  String auth;

    @Value("${redis.pool.maxTotal}")
    private  String maxTotal;

    @Value("${redis.pool.maxIdle}")
    private  String maxIdle;

    @Value("${redis.pool.minIdle}")
    private  String minIdle;

    @Value("${redis.pool.maxWaitMillis}")
    private  String maxWaitMillis;

    @Value("${redis.pool.testOnBorrow}")
    private  String testOnBorrow;

    @Value("${redis.pool.testOnReturn}")
    private  String testOnReturn;

    public void testNoSql()
    {

        //logger.info("test for *******key*******");
        this.setValue("test", "123456789");
        //logger.info("test " + this.getValue("test"));
        this.delKey("test");
        //logger.info("existsKey test1 " + this.existsKey("test"));
        this.setValue("test1", "111111");
        //logger.info("test1 " + this.getValue("test1"));
        this.expireKey("test1", 3);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //logger.info("existsKey test1 " + this.existsKey("test1"));
        //logger.info("test for *******map*******");
        this.hSet("testmap1", "1", "11111");
        this.hSet("testmap1", "2", "22222");
        //logger.info("testmap1 hExists 1 " + this.hExists("testmap1", "1"));
        //logger.info("testmap1 hExists 2 " + this.hExists("testmap1", "2"));
        //logger.info("testmap1 1 " + this.hGet("testmap1", "1"));
        //logger.info("testmap1 2 " + this.hGet("testmap1", "2"));
        //logger.info("hmget " + this.hmGet("testmap1", "1", "2"));
        //logger.info("keys " + this.hKeys("testmap1"));
        //logger.info("values " + this.hValues("testmap1"));
        //logger.info("all " + this.hGetAll("testmap1"));
    }

    void createJedisPool(String rServer, String rAuth, String rMaxTotal, String rMaxIdle, String rMinIdle, String rMaxWaitMillis, String rTestOnBorrow, String rTestOnReturn)
    {
        auth = rAuth;

        try {
            // 创建jedis池配置实例
            JedisPoolConfig config = new JedisPoolConfig();

            // 设置池配置项值
            config.setMaxTotal(Integer.parseInt(rMaxTotal));
            config.setMaxIdle(Integer.parseInt(rMaxIdle));
            config.setMinIdle(Integer.parseInt(rMinIdle));
            config.setMaxWaitMillis(Long.parseLong(rMaxWaitMillis));

            //config.setTestOnBorrow("true".equals(rTestOnBorrow));
            //config.setTestOnReturn("true".equals(rTestOnReturn));

            if(StringUtils.isEmpty(rServer)){
                throw new IllegalArgumentException("JedisPool redis.server is empty!");
            }

            String[] host_arr = rServer.split(",");
            if(host_arr.length>1){
                throw new IllegalArgumentException("JedisPool redis.server length > 1");
            }
            String[] arr = host_arr[0].split(":");
            logger.info("init JedisPool start : host->"+arr[0]+",port->"+arr[1]);
            pool = new JedisPool(config, arr[0], Integer.parseInt(arr[1]), Integer.parseInt(rMaxWaitMillis), auth);
            // 根据配置实例化jedis池
            logger.info("init JedisPool finished");
        } catch (Exception e) {
            throw new IllegalArgumentException("init JedisPool error", e);
        }
    }

    @PostConstruct
    void createJedisPool() {
        createJedisPool(this.redisServer, this.auth, this.maxTotal, this.maxIdle, this.minIdle, this.maxWaitMillis, this.testOnBorrow, this.testOnReturn);
    }

    private Jedis getResource() {

        Jedis jedis = pool.getResource();
        if (jedis != null)
        {
            if (!StringUtils.isBlank(auth))
            {
                //jedis.auth(auth);
            }

            return jedis;
        }

        return null;
    }

    @Override
    public boolean delKey(String key)
    {
        Jedis jedis = getResource();
        if (jedis == null)
        {
            return false;
        }

        try
        {
            Long ret = jedis.del(key);
            if (ret == 1)
            {
                return true;
            }
            return false;
        }
        catch (Exception e)
        {

        }
        finally
        {
            //return back to pool
            jedis.close();
        }

        return false;
    }

    @Override
    public boolean existsKey(String key)
    {
        Jedis jedis = getResource();
        if (jedis == null)
        {
            return false;
        }

        try
        {
            return jedis.exists(key);

        }
        catch (Exception e)
        {

        }
        finally
        {
            //return back to pool
            jedis.close();
        }

        return false;
    }

    @Override
    public boolean expireKey(String key, int secs)
    {
        Jedis jedis = getResource();
        if (jedis == null)
        {
            return false;
        }

        try
        {
            Long ret = jedis.expire(key, secs);
            return true;

        }
        catch (Exception e)
        {

        }
        finally
        {
            //return back to pool
            jedis.close();
        }

        return false;
    }

    @Override
    public boolean expireatKey(String key, int unixTime)
    {
        Jedis jedis = getResource();
        if (jedis == null)
        {
            return false;
        }

        try
        {
            Long ret = jedis.expireAt(key, unixTime);
            return true;
        }
        catch (Exception e)
        {

        }
        finally
        {
            //return back to pool
            jedis.close();
        }

        return false;
    }

    @Override
    public boolean setValue(String key, String value)
    {
        Jedis jedis = getResource();
        if (jedis == null)
        {
            return false;
        }

        try
        {
            String ret = jedis.set(key, value);
            if (ret.equals("OK"))
            {
                return true;
            }
            return false;
        }
        catch (Exception e)
        {

        }
        finally
        {
            //return back to pool
            jedis.close();
        }

        return false;
    }

    @Override
    public String getValue(String key)
    {
        Jedis jedis = getResource();
        if (jedis == null)
        {
            return null;
        }

        try
        {
            return jedis.get(key);
        }
        catch (Exception e)
        {

        }
        finally
        {
            //return back to pool
            jedis.close();
        }

        return null;
    }

    @Override
    public boolean hSet(String key, String field, String value)
    {
        Jedis jedis = getResource();
        if (jedis == null)
        {
            return false;
        }

        try
        {
            Long ret = jedis.hset(key, field, value);
            return true;
        }
        catch (Exception e)
        {

        }
        finally
        {
            //return back to pool
            jedis.close();
        }

        return false;
    }

    @Override
    public String hGet(String key, String field)
    {
        Jedis jedis = getResource();
        if (jedis == null)
        {
            return null;
        }

        try
        {
            return jedis.hget(key, field);
        }
        catch (Exception e)
        {

        }
        finally
        {
            //return back to pool
            jedis.close();
        }

        return null;
    }

    @Override
    public boolean hmSet(String key, Map<String, String> map)
    {
        Jedis jedis = getResource();
        if (jedis == null)
        {
            return false;
        }

        try
        {
            String ret = jedis.hmset(key, map);
            return true;
        }
        catch (Exception e)
        {

        }
        finally
        {
            //return back to pool
            jedis.close();
        }

        return false;
    }

    @Override
    public List<String> hmGet(String key, String... field)
    {
        Jedis jedis = getResource();
        if (jedis == null)
        {
            return null;
        }

        try
        {
            return jedis.hmget(key, field);
        }
        catch (Exception e)
        {

        }
        finally
        {
            //return back to pool
            jedis.close();
        }

        return null;
    }

    @Override
    public boolean hExists(String key, String field)
    {
        Jedis jedis = getResource();
        if (jedis == null)
        {
            return false;
        }

        try
        {
            return jedis.hexists(key, field);
        }
        catch (Exception e)
        {

        }
        finally
        {
            //return back to pool
            jedis.close();
        }

        return false;
    }

    @Override
    public boolean hDel(String key, String field)
    {
        Jedis jedis = getResource();
        if (jedis == null)
        {
            return false;
        }

        try
        {
            Long ret = jedis.hdel(key, field);
            return true;
        }
        catch (Exception e)
        {

        }
        finally
        {
            //return back to pool
            jedis.close();
        }

        return false;
    }

    @Override
    public int hLength(String key)
    {
        Jedis jedis = getResource();
        if (jedis == null)
        {
            return 0;
        }

        try
        {
            Long ret = jedis.hlen(key);
            return toIntExact(ret);
        }
        catch (Exception e)
        {

        }
        finally
        {
            //return back to pool
            jedis.close();
        }

        return 0;
    }

    @Override
    public Set<String> hKeys(String key)
    {
        Jedis jedis = getResource();
        if (jedis == null)
        {
            return null;
        }

        try
        {
            return jedis.hkeys(key);
        }
        catch (Exception e)
        {

        }
        finally
        {
            //return back to pool
            jedis.close();
        }

        return null;
    }

    @Override
    public List<String> hValues(String key)
    {
        Jedis jedis = getResource();
        if (jedis == null)
        {
            return null;
        }

        try
        {
            return jedis.hvals(key);
        }
        catch (Exception e)
        {

        }
        finally
        {
            //return back to pool
            jedis.close();
        }

        return null;
    }

    @Override
    public Map<String, String> hGetAll(String key)
    {
        Jedis jedis = getResource();
        if (jedis == null)
        {
            return null;
        }

        try
        {
            return jedis.hgetAll(key);
        }
        catch (Exception e)
        {

        }
        finally
        {
            //return back to pool
            jedis.close();
        }

        return null;
    }

    @Override
    public long incr(String key)
    {
        Jedis jedis = getResource();
        if (jedis == null)
        {
            return 0;
        }

        try
        {
            return jedis.incr(key);
        }
        catch (Exception e)
        {

        }
        finally
        {
            //return back to pool
            jedis.close();
        }

        return 0;
    }


    @Override
    public void incrByFloat(String key, float f)
    {
        Jedis jedis = getResource();
        if (jedis == null)
        {
            return;
        }

        try
        {
            jedis.incrByFloat(key, f);
        }
        catch (Exception e)
        {

        }
        finally
        {
            //return back to pool
            jedis.close();
        }

        return;
    }
}
