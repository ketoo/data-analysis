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
    
    //for saving
    private static List<NFDailyNewUsersModel> mxModelList = new ArrayList<>();
    
    
    @Override
    public List<String> getNewUserList()
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
                    String loginCount = elements[NFInputType.PlayerLogin.LoginCount.getId()];
                    Integer nCount = Integer.parseInt(loginCount);
                
                    //登录次数为0-1次的，才是第一次登录，新增用户
                    if (nCount <= 1)
                    {
                        if (newUserList.containsKey(openID))
                        {
                            newUserList.put(openID, nCount);
                        }
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
    public List<String> getNewDeviceList()
    {
        HashMap<String, Integer> newDeviceList = new HashMap<String, Integer>();
    
        List<String> strList = logModule.getLogData(NFLogType.LOG_REGISTER);
        if (strList != null)
        {
            for (int i = 0; i < strList.size(); ++i)
            {
                String line = strList.get(i);
            
                String[] elements = StringUtils.split(line, '|');
                if (elements.length == NFInputType.PlayerRegisterType.values().length)
                {
                    String deviceID = elements[NFInputType.PlayerRegisterType.DeviceId.getId()];
                    if (!nosqlBzModule.existDevice(deviceID))
                    {
                        if (newDeviceList.containsKey(deviceID))
                        {
                            newDeviceList.put(deviceID, 0);
                        }
                    }
                }
            }
        }
    
        List<String> deviceList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: newDeviceList.entrySet())
        {
            deviceList.add(entry.getKey());
        }
    
        return deviceList;
    }
    
    @Override
    public void reset()
    {
        mxLineList.clear();
        mxPlatMap.clear();
        mxModelList.clear();
    }

    //留存
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
                    String openID = elements[NFInputType.PlayerLogin.Openid.getId()];
                    String loginCount = elements[NFInputType.PlayerLogin.LoginCount.getId()];
                    Integer nCount = Integer.parseInt(loginCount);
                    
                    //登录次数为0-1次的，才是第一次登录，新增用户
                    if (nCount <= 1)
                    {
                        if (!xNewUserMap.containsKey(openID))
                        {
                            xNewUserMap.put(openID, 1);
                            mxLineList.add(line);
                        }
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
                String loginCount = elements[NFInputType.PlayerLogin.LoginCount.getId()];
                Integer nLoginCount = Integer.parseInt(loginCount);
                if (nLoginCount <= 1)
                {
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
    }

    @Override
    public void doBusinessAnalyse()
    {
        //for all devices
        List<String> strList = logModule.getLogData(NFLogType.LOG_REGISTER);
        if (strList != null)
        {
            for (int i = 0; i < strList.size(); ++i)
            {
                String line = strList.get(i);
                String[] elements = StringUtils.split(line, '|');
                if (elements.length == NFInputType.PlayerRegisterType.values().length)
                {
                    String deviceID = elements[NFInputType.PlayerRegisterType.DeviceId.getId()];
                    nosqlBzModule.addToTotalDeviceList(deviceID);
                }
            }
        }
        
        for (Map.Entry<String, Integer> entry : mxPlatMap.entrySet())
        {
            NFDailyNewUsersModel xModel = new NFDailyNewUsersModel();
            
            xModel.setAppid("");
            xModel.setZoneid("");
            xModel.setPlat(entry.getKey());
            xModel.setNumber(entry.getValue());
            xModel.setToday_number(mxLineList.size());
            xModel.setTotal_number(nosqlBzModule.getTotalUserCount());
            xModel.setTotal_device(nosqlBzModule.getTotalDeviceCount());

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
        
        //存储所有的新增账号（按day存，方便计算留存）
        for (int i = 0; i < mxLineList.size(); ++i)
        {
            String line = mxLineList.get(i);
            String[] elements = StringUtils.split(line,'|');
            if (elements.length == NFInputType.PlayerLogin.values().length)
            {
                //2分析平台
                String keyPlat = elements[NFInputType.PlayerLogin.PlatID.getId()];
                String openID = elements[NFInputType.PlayerLogin.Openid.getId()];
                String loginCount = elements[NFInputType.PlayerLogin.LoginCount.getId()];
                
            }
        }
    
        //storage all new user
        List<String> xUserList = getNewUserList();
        
        for (int i = 0; i < xUserList.size(); ++i)
        {
            String userID = xUserList.get(i);
            //总用户
            nosqlBzModule.addToTotalUserList(userID, 0);
        
            //每日用户
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            nosqlBzModule.addToDailyNewUserList(calendar, userID);
        }
    }
}
