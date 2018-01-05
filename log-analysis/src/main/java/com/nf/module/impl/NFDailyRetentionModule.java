package com.nf.module.impl;

import com.nf.comm.NFInputType;
import com.nf.comm.NFLogType;
import com.nf.model.NFDailyRetentionModel;
import com.nf.module.NFIDailyRetentionModule;
import com.nf.module.NFILogModule;
import com.nf.nosqlservice.NFINosqlModule;
import org.apache.commons.lang.StringUtils;
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
    
    
    private static List<String> mxLineList = new ArrayList<>();
    
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
        //1 去重
        List<String> strList = logModule.getLogData(NFLogType.LOG_REGISTER);
        if (strList != null)
        {
            Map<String, Integer> xNewUserMap = new HashMap<String, Integer>();
        
            for (int i = 0; i < strList.size(); ++i)
            {
                String line = strList.get(i);
            
                String[] elements = StringUtils.split(line, '|');
                if (elements.length == NFInputType.PlayerRegisterType.values().length)
                {
                    String key = elements[NFInputType.PlayerRegisterType.DeviceId.getId()];
                    if (!xNewUserMap.containsKey(key))
                    {
                        xNewUserMap.put(key, 1);
                        mxLineList.add(line);
                    }
                }
            }
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
