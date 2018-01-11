package com.nf.module.impl;

import com.nf.comm.NFLogType;
import com.nf.module.NFILogModule;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lushenghuang on 20/12/17.
 */

@Service
public class NFLogModule implements NFILogModule
{
    private static Map<NFLogType, List<String>> mxLogData = new HashMap<NFLogType, List<String>>();
    private static Calendar calendar;
    private static String mstrProgress;
    private static String mstrModuleName;
    
    private int mnMinElement = 5;

    @Override
    public void reset()
    {
        mxLogData.clear();
    }

    @Override
    public void doBusinessAnalyse()
    {
    }

    @Override
    public void saveBusinessAnalyseResult()
    {
        //it would release some objects for saving memory
    }

    @Override
    public List<String> getLogData(NFLogType type)
    {
        return mxLogData.get(type);
    }
    
    @Override
    public String getLogDate()
    {
        //calendar.add(Calendar.DAY_OF_YEAR, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }
    
    @Override
    public Calendar getLogCalendar()
    {
        return calendar;
    }
    
    @Override
    public void setLogDate(Calendar cl)
    {
        calendar = cl;
    }
    
    @Override
    public void setLogBAProgress(String strProgress)
    {
        mstrProgress = strProgress;
    }
    
    @Override
    public String getLogBAProgress()
    {
        return mstrProgress;
    }
    
    @Override
    public void setLogBAProgressModule(String moduleName)
    {
        mstrModuleName = moduleName;
    }
    
    @Override
    public String getLogBAProgressModule()
    {
        return mstrModuleName;
    }
    
    @Override
    public void map()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(calendar.getTime());
        //String filePath = "./" + time;
        String filePath = "./logs";

        File root = new File(filePath);
        File[] files = root.listFiles();
        if (files == null)
        {
            return;
        }
        
        for(File file:files)
        {
            if(file.isDirectory())
            {
                continue;
            }
            
            if (file.getName().indexOf("info") < 0
                    ||file.getName().indexOf(time) < 0 )
            {
                continue;
            }

            try
            {
                Map<String, NFLogType> logTypeMap = new HashMap<>();
                for (NFLogType e : NFLogType.values())
                {
                    logTypeMap.put(e.getFlag(), e);
                }

                String encoding = "GBK";
                InputStreamReader read = null;// 考虑到编码格式
                read = new InputStreamReader( new FileInputStream(file.getAbsoluteFile()), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                while ((lineTxt = bufferedReader.readLine()) != null)
                {
                    String[] elements = StringUtils.split(lineTxt, '|');
                    if (elements.length > mnMinElement)
                    {
                        //String[] elements = lineTxt.split("|");
                        String strKey = elements[0];
    
                        NFLogType logType = logTypeMap.get(strKey);
                        if (logType != null)
                        {
                            List<String> logLineList = mxLogData.get(logType);
                            if (logLineList == null)
                            {
                                logLineList = new ArrayList<>();
                                mxLogData.put(logType, logLineList);
                            }
        
                            logLineList.add(lineTxt);
                        }
                    }
                }

                bufferedReader.close();
                read.close();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void reduce()
    {

    }
}
