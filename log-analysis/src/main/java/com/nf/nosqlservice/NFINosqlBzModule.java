package com.nf.nosqlservice;

import java.util.Calendar;

/**
 * Created by James on 23/12/17.
 */
public interface NFINosqlBzModule
{
    void addToTotalUserList(String user, int onlineTime);
    int getTotalUserCount();
    int getAVGUserTotalOnlineTime();
    
    void addToTotalDeviceList(String device);
    int getTotalDeviceCount();
    boolean existDevice(String device);
    
    
    void addToDailyNewUserList(Calendar calendar, String user);
    int getDailyNewUserCount(Calendar calendar);
    boolean existDailyNewUser(Calendar calendar, String user);
}
