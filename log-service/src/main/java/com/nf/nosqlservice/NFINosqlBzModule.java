package com.nf.nosqlservice;

import java.util.Calendar;

/**
 * Created by James on 23/12/17.
 */
public interface NFINosqlBzModule
{
    void addToTotalUserList(String user);
    int getTotalUserCount();
    
    void addToTotalDeviceList(String device);
    int getTotalDeviceCount();
    
    
    void addToDailyUserList(Calendar calendar, String user);
    int getDailyUserCount(Calendar calendar);
    boolean existDailyUser(Calendar calendar, String user);
}
