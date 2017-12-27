package com.nf.module.impl;

import com.nf.comm.NFLogType;
import com.nf.model.NFDailyRetentionModel;
import com.nf.module.NFIDailyRetentionModule;
import com.nf.module.NFILogModule;
import com.nf.nosqlservice.NFINosqlModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lushenghuang on 20/12/17.
 */
@Service
public class NFDailyRetentionModule implements NFIDailyRetentionModule
{
    @Autowired
    private NFILogModule logModule;

    @Autowired
    private NFINosqlModule nosqlModule;
    
    
    private static Map<String, Integer> mxPlatMap = new HashMap<String, Integer>();
    
    
    //for saving
    private static List<NFDailyRetentionModel> mxDailyRetentionModelList = new ArrayList<>();
    
    
    @Override
    public void reset()
    {
    
        mxDailyRetentionModelList.clear();
    }


    @Override
    public void doBusinessAnalyse()
    {

    }

    @Override
    public void saveBusinessAnalyseResult()
    {

    }

    @Override
    public void map()
    {
        //先找出今天登陆的玩家，去重和统计次数
        //1 去重
        List<String> strList = logModule.getLogData(NFLogType.LOG_LOGIN);
        if (strList != null)
        {
            Map<String, Integer> xNewUserMap = new HashMap<String, Integer>();
        }
    }

    @Override
    public void reduce()
    {

    }
    
    //次日留存就传2，3日就传3
    private void Retention(int nDay)
    {
    
    }
    
}
