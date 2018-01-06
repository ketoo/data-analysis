package com.nf.nosqlservice.impl;

import com.nf.nosqlservice.NFINosqlBzModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by James on 23/12/17.
 */

@Service
public class NFNosqlBzModule implements NFINosqlBzModule
{
    @Autowired
    private NFNosqlModule nosqlModule;
    
    
    private String strTotalUserKey = "TotalUserKey";
    private String strTotalDeviceKey = "TotalDeviceKey";
    private String strDailyUserKey = "DailyUserKey";
    private String strTotalUserOnlineTime = "TotalUserOnlineTime";
    
    @Override
    public void addToTotalUserList(String user, int onlineTime)
    {
        Integer time = onlineTime;
        if (!nosqlModule.existsKey(strTotalUserKey))
        {
            nosqlModule.hSet(strTotalUserKey, user, time.toString());
        }
        else
        {
            if (nosqlModule.hExists(strTotalUserKey, user))
            {
                String strValue = nosqlModule.hGet(strTotalUserKey, user);
                Integer lastOnlineTime = Integer.valueOf(strValue);
    
                lastOnlineTime += time;
                
                nosqlModule.hSet(strTotalUserKey, user, lastOnlineTime.toString());
            }
            else
            {
                nosqlModule.hSet(strTotalUserKey, user, time.toString());
            }
        }
        
        if (time > 0)
        {
            if (!nosqlModule.existsKey(strTotalUserOnlineTime))
            {
                nosqlModule.setValue(strTotalUserOnlineTime, time.toString());
            }
            else
            {
                String strValue = nosqlModule.getValue(strTotalUserOnlineTime);
                Integer lastOnlineTime = Integer.valueOf(strValue);
    
                lastOnlineTime += time;
    
                nosqlModule.setValue(strTotalUserOnlineTime, lastOnlineTime.toString());
            }
        }
    }
    
    @Override
    public int getTotalUserCount()
    {
        return nosqlModule.hLength(strTotalUserKey);
    }
    
    @Override
    public int getAVGUserTotalOnlineTime()
    {
        int nAllUserCount = getTotalUserCount();
        String strValue = nosqlModule.getValue(strTotalUserOnlineTime);
        Integer allOnlineTime = Integer.valueOf(strValue);
    
        if (nAllUserCount > 0)
        {
            return allOnlineTime / nAllUserCount;
        }
        
        return 0;
    }
    
    @Override
    public void addToTotalDeviceList(String device)
    {
        nosqlModule.hSet(strTotalDeviceKey, device, "1");
    }
    
    @Override
    public int getTotalDeviceCount()
    {
        return nosqlModule.hLength(strTotalDeviceKey);
    }
    
    @Override
    public boolean existDevice(String device)
    {
        return nosqlModule.hExists(strTotalDeviceKey, device);
    }
    
    @Override
    public void addToDailyNewUserList(Calendar calendar, String user)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String key = strDailyUserKey + sdf.format(calendar.getTime());
        
        nosqlModule.hSet(key, user, "1");
    }
    
    @Override
    public int getDailyNewUserCount(Calendar calendar)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String key = strDailyUserKey + sdf.format(calendar.getTime());
        
        return nosqlModule.hLength(key);
    }
    
    @Override
    public boolean existDailyNewUser(Calendar calendar, String user)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String key = strDailyUserKey + sdf.format(calendar.getTime());
    
        return nosqlModule.hExists(key, user);
    }
}
