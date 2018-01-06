package com.nf.module.impl;

import com.nf.DAO.NFDailyActivelyUsesDAO;
import com.nf.comm.NFInputType;
import com.nf.comm.NFLogType;
import com.nf.model.NFDailyActivelyUsersModel;
import com.nf.module.NFIDailyActivelyUsersModule;
import com.nf.module.NFILogModule;
import com.nf.nosqlservice.NFINosqlBzModule;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lushenghuang on 20/12/17.
 */
@Service
public class NFDailyActivelyUsersModule implements NFIDailyActivelyUsersModule
{
    
    //活跃用户
    @Autowired
    private NFILogModule logModule;
    
    @Autowired
    private NFINosqlBzModule nosqlBzModule;
    
    
    @Autowired
    NFDailyActivelyUsesDAO dailyActivelyUsesDAO;
    
    private static List<String> mxLineList = new ArrayList<>();
    
    //每一个平台的人
    private static Map<String, Integer> mxPlatMap = new HashMap<String, Integer>();
    
    //for saving
    private static List<NFDailyActivelyUsersModel> mxModelList = new ArrayList<>();
    
    
    private static float mfAvgTime = 0;
    
    //每天登陆过的人，都要存储到redis中
    @Override
    public void reset()
    {
        mfAvgTime = 0;
        mxLineList.clear();
        mxPlatMap.clear();
        mxModelList.clear();
    }
    
    @Override
    public  List<String> getLoginedUserList()
    {
        HashMap<String, Integer> newUserList = new HashMap<String, Integer>();
    
        List<String> strList = logModule.getLogData(NFLogType.LOG_LOGIN);
        if (strList != null)
        {
            for (int i = 0; i < strList.size(); ++i)
            {
                String line = strList.get(i);
            
                String[] elements = StringUtils.split(line, '|');
                if (elements.length == NFInputType.PlayerLogin.values().length)
                {
                    String openID = elements[NFInputType.PlayerLogin.Openid.getId()];
                
                    //登录次数为0-1次的，才是第一次登录，新增用户
                    if (newUserList.containsKey(openID))
                    {
                        newUserList.put(openID, 0);
                    }
                }
            }
        }
    
        List<String> userList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: newUserList.entrySet())
        {
            userList.add(entry.getKey());
        }
    
        return userList;
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
        
        double fTotalOnlinetime = 0;
        //统计平均时间--logout
        List<String> strList = logModule.getLogData(NFLogType.LOG_LOGOUT);
        if (strList != null)
        {
            for (int i = 0; i < strList.size(); ++i)
            {
                String line = strList.get(i);
                String[] elements = StringUtils.split(line, '|');
                if (elements.length == NFInputType.PlayerLogout.values().length)
                {
                    String onlineTime = elements[NFInputType.PlayerLogout.OnlineTime.getId()];
                    
                    fTotalOnlinetime += Float.parseFloat(onlineTime);
                }
            }
        }
        
        if (mxLineList.size() > 0)
        {
            mfAvgTime = (float)(fTotalOnlinetime / mxLineList.size());
        }
    }

    @Override
    public void doBusinessAnalyse()
    {
        for (Map.Entry<String, Integer> entry : mxPlatMap.entrySet())
        {
            NFDailyActivelyUsersModel xModel = new NFDailyActivelyUsersModel();
            xModel.setAppid("");
            xModel.setZoneid("");
            xModel.setPlat(entry.getKey());
            xModel.setNumber(entry.getValue());
            xModel.setTotal_number(mxLineList.size());
            xModel.setAvg_time(mfAvgTime);
            
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            xModel.setTime(sdf.format(calendar.getTime()));
    
            mxModelList.add(xModel);
        }
    }

    @Override
    public void saveBusinessAnalyseResult()
    {
        for (int i = 0; i < mxModelList.size(); ++i)
        {
            dailyActivelyUsesDAO.saveAndFlush(mxModelList.get(i));
        }
    }

}
