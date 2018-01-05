package com.nf.module.impl;

import com.nf.DAO.NFDailyNewUsersDAO;
import com.nf.comm.NFInputType;
import com.nf.comm.NFLogType;
import com.nf.model.NFDailyNewUsersModel;
import com.nf.module.NFIDailyNewUsersModule;
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
public class NFDailyNewUsersModule implements NFIDailyNewUsersModule
{
    //新增用户
    @Autowired
    private NFILogModule logModule;
    
    @Autowired
    private NFINosqlBzModule nosqlBzModule;

    @Autowired
    private NFDailyNewUsersDAO dailyNewUsersDAO;

    private static List<String> mxLineList = new ArrayList<>();

    private static Map<String, Integer> mxPlatMap = new HashMap<String, Integer>();
    
    
    private static List<String> mxDevicesList = new ArrayList<>();
    
    //for saving
    private static List<NFDailyNewUsersModel> mxModelList = new ArrayList<>();


    @Override
    public void reset()
    {
        mxDevicesList.clear();
        mxLineList.clear();
        mxPlatMap.clear();
        mxModelList.clear();
    }

    //留存
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
        for (int i = 0; i < mxLineList.size(); ++i)
        {
            String line = mxLineList.get(i);
            String[] elements = StringUtils.split(line,'|');
            if (elements.length == NFInputType.PlayerRegisterType.values().length)
            {
                //2分析平台
                String keyPlat = elements[NFInputType.PlayerRegisterType.PlatID.getId()];
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
    
    
                //记录所有的设备
                String deviceID = elements[NFInputType.PlayerRegisterType.DeviceId.getId()];
                mxDevicesList.add(deviceID);
            }
        }

    }

    @Override
    public void doBusinessAnalyse()
    {
        for (Map.Entry<String, Integer> entry : mxPlatMap.entrySet())
        {
            NFDailyNewUsersModel xModel = new NFDailyNewUsersModel();

            xModel.setAppid("");
            xModel.setZoneid("");
            xModel.setPlat(entry.getKey());
            xModel.setNumber(entry.getValue());
            xModel.setTotal_number(mxLineList.size());

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
            dailyNewUsersDAO.saveAndFlush(mxModelList.get(i));
        }
        
        //storage all new device
        for (int i = 0; i < mxDevicesList.size(); ++i)
        {
            String device = mxDevicesList.get(i);
            nosqlBzModule.addToTotalDeviceList(device);
        }
    }
}
