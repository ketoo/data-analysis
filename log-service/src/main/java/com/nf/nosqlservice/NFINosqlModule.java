package com.nf.nosqlservice;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lushenghuang on 20/12/17.
 */
public interface NFINosqlModule
{
    void testNoSql();

    boolean delKey(String key);
    boolean existsKey(String key);
    boolean expireKey(String key, int secs);
    boolean expireatKey(String key, int unixTime);

    ///////////////////////////////////////////////////////////

    boolean setValue(String key, String value);
    String getValue(String key);

    ///////////////////////////////////////////////////////////

    boolean hSet(String key, String strField, String value);
    String hGet(String key, String strField);

    boolean hmSet(String key, Map<String, String> map);
    List<String> hmGet(String key, String... field);

    boolean hExists(String key, String strField);
    boolean hDel(String key, String strField);
    int hLength(String key);

    Set<String> hKeys(String key);
    List<String> hValues(String key);
    Map<String, String> hGetAll(String key);

    long incr(String key);
    void incrByFloat(String key, float f);
}
