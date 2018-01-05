package com.nf.module.impl;

import com.nf.DAO.NFDailyAVGOnlineTimeDAO;
import com.nf.comm.NFInputType;
import com.nf.comm.NFLogType;
import com.nf.model.NFDailyAVGOnlineTimeModel;
import com.nf.model.NFDailyActivelyUsersModel;
import com.nf.module.NFIDailyAVGOnlineTimeModule;
import com.nf.module.NFILogModule;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lushenghuang on 20/12/17.
 */
@Service
public class NFDailyAVGOnlineTimeModule implements NFIDailyAVGOnlineTimeModule
{
    @Autowired
    private NFILogModule logModule;

    @Autowired
    private NFDailyAVGOnlineTimeDAO dailyAVGOnlineTimeDAO;
    
    private static List<String> mxLineList = new ArrayList<>();
    //for saving
    private static List<NFDailyAVGOnlineTimeModel> mxModelList = new ArrayList<>();
    
    private static float mfAVGTime = 0;
    private static float mfAVGLevel = 0;
    
    @Override
    public void reset()
    {
        mxLineList.clear();
    }


    @Override
    public void doBusinessAnalyse()
    {
        NFDailyAVGOnlineTimeModel xModel = new NFDailyAVGOnlineTimeModel();
        
        xModel.setAppid("");
        xModel.setZoneid("");
        xModel.setPlat("");
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        xModel.setTime(sdf.format(calendar.getTime()));
        
        xModel.setTotal_number(mxLineList.size());
        xModel.setAvg_time(mfAVGTime);
        xModel.setAvg_level(mfAVGLevel);
    
    
    
        mxModelList.add(xModel);
    }

    @Override
    public void saveBusinessAnalyseResult()
    {
        for (int i= 0; i < mxModelList.size(); ++i)
        {
            dailyAVGOnlineTimeDAO.saveAndFlush(mxModelList.get(i));
        }
    
    }

    @Override
    public void map()
    {
        //1 去重
        List<String> strList = logModule.getLogData(NFLogType.LOG_LOGOUT);
        if (strList != null)
        {
            Map<String, Integer> xNewUserMap = new HashMap<String, Integer>();
        
            for (int i = 0; i < strList.size(); ++i)
            {
                String line = strList.get(i);
            
                String[] elements = StringUtils.split(line, '|');
                if (elements.length == NFInputType.PlayerLogout.values().length)
                {
                    String key = elements[NFInputType.PlayerLogout.Openid.getId()];
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
        List<String> strList = logModule.getLogData(NFLogType.LOG_LOGOUT);
        if (strList != null)
        {
            double fTime = 0;
            double fLevel = 0;
            
            for (int i = 0; i < strList.size(); ++i)
            {
                String line = strList.get(i);
                
                String[] elements = StringUtils.split(line, '|');
                if (elements.length == NFInputType.PlayerLogout.values().length)
                {
                    String time = elements[NFInputType.PlayerLogout.OnlineTime.getId()];
                    fTime += Double.parseDouble(time);
    
                    String level = elements[NFInputType.PlayerLogout.Level.getId()];
                    fLevel += Double.parseDouble(level);
                    
                }
            }
    
            mfAVGTime = (float) (fTime / mxLineList.size());
            mfAVGLevel = (float) (fLevel / mxLineList.size());
        }
    }
}
