package com.nf.module.impl;

import com.nf.DAO.NFDailyRetentionDAO;
import com.nf.comm.NFInputType;
import com.nf.comm.NFLogType;
import com.nf.model.NFDailyRetentionModel;
import com.nf.module.NFIDailyRetentionModule;
import com.nf.module.NFILogModule;
import com.nf.nosqlservice.NFINosqlBzModule;
import com.nf.nosqlservice.NFINosqlModule;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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
    
    @Autowired
    private NFINosqlBzModule nosqlBzModule;
    
    @Autowired
    private NFDailyRetentionDAO dailyRetentionDAO;
    
    @Autowired
    private NFDailyActivelyUsersModule dailyActivelyUsersModule;
    
    private static Map<String, Integer> mxPlatMap = new HashMap<String, Integer>();
    
    
    private static List<String> mxLineList = new ArrayList<>();
    
    //for saving
    private static List<NFDailyRetentionModel> mxModelList = new ArrayList<>();
    
    @Override
    public void reset()
    {
        mxLineList.clear();
        mxPlatMap.clear();
        mxModelList.clear();
    }


    @Override
    public void map()
    {
        //1 去重
        List<String> strList = logModule.getLogData(NFLogType.LOG_LOGIN);
        if (strList != null)
        {
            Map<String, Integer> xNewUserMap = new HashMap<String, Integer>();
        
            for (int i = 0; i < strList.size(); ++i)
            {
                String line = strList.get(i);
            
                String[] elements = StringUtils.split(line, '|');
                if (elements.length == NFInputType.PlayerLogin.values().length)
                {
                    String key = elements[NFInputType.PlayerLogin.Openid.getId()];
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
        for (int i = 0; i < mxLineList.size(); ++i)
        {
            String line = mxLineList.get(i);
            String[] elements = StringUtils.split(line,'|');
            if (elements.length == NFInputType.PlayerLogin.values().length)
            {
                //2分析平台
                String keyPlat = elements[NFInputType.PlayerLogin.PlatID.getId()];
                if (!mxPlatMap.containsKey(keyPlat))
                {
                    mxPlatMap.put(keyPlat, 1);
                }
                else
                {
                    Integer number = mxPlatMap.get(keyPlat);
                    number++;
                    mxPlatMap.replace(keyPlat, number);
                }
            }
        }
        
    }
    
    @Override
    public void doBusinessAnalyse()
    {
        for (int i = 2; i < 15; i++)
        {
            Retention(i);
        }
    }
    
    @Override
    public void saveBusinessAnalyseResult()
    {
        for (int i = 0; i < mxModelList.size(); ++i)
        {
            dailyRetentionDAO.saveAndFlush(mxModelList.get(i));
        }
    }
    
    //次日留存就传2，3日就传3
    private void Retention(int nDay)
    {
        int nCount = 0;
        NFDailyRetentionModel xModel = new NFDailyRetentionModel();
    
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -nDay);
    
        //看这些玩家，在之前n日是否是新增
        List<String> xLoginedUser = dailyActivelyUsersModule.getLoginedUserList();
        for (int i = 0; i < xLoginedUser.size(); ++i)
        {
            if (nosqlBzModule.existDailyNewUser(calendar, xLoginedUser.get(i)))
            {
                nCount++;
            }
        }
    
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        xModel.setTime(sdf.format(calendar.getTime()));
        xModel.setDay(nDay);
        xModel.setTotal_number(nCount);
        xModel.setRate(nCount / nosqlBzModule.getDailyNewUserCount(calendar));
        mxModelList.add(xModel);
    }
    
}
