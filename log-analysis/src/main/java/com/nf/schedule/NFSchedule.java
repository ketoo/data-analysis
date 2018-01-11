package com.nf.schedule;

import com.nf.module.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lushenghuang on 20/12/17.
 */

@Service
public class NFSchedule
{
    static List<NFIBAModule> mxModuleList =  new ArrayList<>();

    private final Logger logger = LoggerFactory.getLogger(getClass());
    public final static long ONE_MIN =  60 * 1000;
    public final static long ONE_SECOND =  1000;

    @Autowired
    private NFILogModule logModule;

    @Autowired
    private NFIActivityFlowModule activityFlowModule;

    @Autowired
    private NFIAPIFlowModule apiFlowModule;

    @Autowired
    private NFIBlockFlowModule blockFlowModule;

    @Autowired
    private NFIDailyActivelyUsersModule dailyActivelyUsersModule;

    @Autowired
    private NFIDailyAVGOnlineTimeModule dailyAVGOnlineTimeModule;

    @Autowired
    private NFIDailyNewUsersModule dailyNewUsersModule;

    @Autowired
    private NFIDailyRetentionModule dailyRetentionModule;

    @Autowired
    private NFIItemFlowModule itemFlowModule;

    @Autowired
    private NFIMoneyFlowModule moneyFlowModule;

    @Autowired
    private NFIPayFlowModule payFlowModule;

    @Autowired
    private NFIPlayerBehaviourFlowModule playerBehaviourFlowModule;

    @Autowired
    private NFIRoundFlowModule roundFlowModule;

    @Autowired
    private NFITaskFlowModule taskFlowModule;


    private Date lastTime = new Date();

    @Scheduled(fixedDelay = ONE_SECOND * 10)
    public void scheduleTaskforBA()
    {
        Date nowTime = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(lastTime);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(nowTime);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        //if (!isSameDate)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            DoBATask(calendar);
        }
    }
    
    private void initModuleList()
    {
        mxModuleList.add(logModule);
        mxModuleList.add(dailyNewUsersModule);
        mxModuleList.add(dailyRetentionModule);
        mxModuleList.add(activityFlowModule);
        mxModuleList.add(apiFlowModule);
        mxModuleList.add(blockFlowModule);
        mxModuleList.add(dailyActivelyUsersModule);
        mxModuleList.add(dailyAVGOnlineTimeModule);
        mxModuleList.add(itemFlowModule);
        mxModuleList.add(moneyFlowModule);
        mxModuleList.add(payFlowModule);
        mxModuleList.add(playerBehaviourFlowModule);
        mxModuleList.add(roundFlowModule);
        mxModuleList.add(taskFlowModule);
    
    }
    
    public void DoBATask(Calendar cl)
    {
        if (mxModuleList.size() <= 0)
        {
            initModuleList();
        }
    
        logModule.setLogDate(cl);
        
        logModule.setLogBAProgress(String.format("%d/%d", 0, mxModuleList.size()));
        logModule.setLogBAProgressModule("");
        
        for (int i = 0; i < mxModuleList.size(); ++i)
        {
            mxModuleList.get(i).map();
            mxModuleList.get(i).reduce();
            mxModuleList.get(i).doBusinessAnalyse();
            mxModuleList.get(i).saveBusinessAnalyseResult();
    
            String strProgress = String.format("%d/%d", i, mxModuleList.size());
            
            logModule.setLogBAProgress(strProgress);
            logModule.setLogBAProgressModule(mxModuleList.get(i).getClass().getName());
        }
    
    }
}
