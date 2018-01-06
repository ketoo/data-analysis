package com.nf.module;

import java.util.List;

/**
 * Created by lushenghuang on 20/12/17.
 */
public interface NFIDailyNewUsersModule extends NFIBAModule
{
    List<String> getNewUserList();
    List<String> getNewDeviceList();
}
