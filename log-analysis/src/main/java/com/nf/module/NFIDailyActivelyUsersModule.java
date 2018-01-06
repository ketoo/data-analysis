package com.nf.module;

import java.util.List;

/**
 * Created by lushenghuang on 20/12/17.
 */
public interface NFIDailyActivelyUsersModule extends NFIBAModule
{
    List<String> getLoginedUserList();
}
