package com.nf.comm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lushenghuang on 20/12/17.
 */
public enum NFLogType
{
    LOG_UNKNOW(0, "UNKNOW"),
    LOG_REGISTER(1, "PlayerRegister"),
    LOG_LOGIN(2, "PlayerLogin"),
    LOG_LOGOUT(3, "PlayerLogout"),
    LOG_LEVEL(4, "PlayerLevelFlow"),
    LOG_ROUND(5, "RoundFlow"),
    LOG_BEHAVIOUR(6, "PlayerBehaviourFlow"),
    LOG_TASK(7, "TaskFlow"),
    LOG_BLOCK(8, "BlockFlow"),
    LOG_ACTIVITY(9, "ActivityFlow"),
    LOG_API(10, "APIFlow"),
    LOG_PAY(11, "PayFlow"),
    LOG_MONEY(12, "MoneyFlow"),
    LOG_ITEM(13, "ItemFlow");

    NFLogType(int code, String flag)
    {
        this.code = code;
        this.flag = flag;
    }

    public static Map getEnumMap()
    {
        NFLogType[] enums = NFLogType.values();
        Map m = new HashMap();
        int c = NFLogType.values().length;
        for (int i = 0; i < c; i++)
        {
            m.put(enums[i].code, enums[i]);
        }
        return m;
    }

    public static NFLogType getEnum(int code)
    {
        NFLogType[] enums = NFLogType.values();
        int c = NFLogType.values().length;
        for (int i = 0; i < c; i++)
        {
            if (i == code)
            {
                return enums[i];
            }
        }
        return null;
    }

    public static NFLogType getEnum(String desc)
    {
        NFLogType[] enums = NFLogType.values();
        int c = NFLogType.values().length;
        for (int i = 0; i < c; i++)
        {
            if (enums[i].flag.equals(desc.toUpperCase()))
            {
                return enums[i];
            }
        }
        return null;
    }

    public int getCode()
    {
        return this.code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getFlag()
    {
        return this.flag;
    }

    public void setFlag(String flag)
    {
        this.flag = flag;
    }

    private int code;
    private String flag;
}