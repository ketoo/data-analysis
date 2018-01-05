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
    
    @Override
    public void addToTotalUserList(String user)
    {
        nosqlModule.hSet(strTotalUserKey, user, "1");
    }
    
    @Override
    public int getTotalUserCount()
    {
        return nosqlModule.hLength(strTotalUserKey);
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
    public void addToDailyUserList(Calendar calendar, String user)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String key = strDailyUserKey + sdf.format(calendar.getTime());
        
        nosqlModule.hSet(key, user, "1");
    }
    
    @Override
    public int getDailyUserCount(Calendar calendar)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String key = strDailyUserKey + sdf.format(calendar.getTime());
        
        return nosqlModule.hLength(key);
    }
    
    @Override
    public boolean existDailyUser(Calendar calendar, String user)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String key = strDailyUserKey + sdf.format(calendar.getTime());
    
        return nosqlModule.hExists(key, user);
    }
}
