package com.nf.schedule;

import com.nf.module.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by lushenghuang on 20/12/17.
 */

@Service
public class NFSchedule
{

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
            DoBATask();
        }
    }

    private void DoBATask()
    {
        logModule.map();
        logModule.reduce();
        logModule.doBusinessAnalyse();
        logModule.saveBusinessAnalyseResult();

        activityFlowModule.map();
        activityFlowModule.reduce();
        activityFlowModule.doBusinessAnalyse();
        activityFlowModule.saveBusinessAnalyseResult();

        apiFlowModule.map();
        apiFlowModule.reduce();
        apiFlowModule.doBusinessAnalyse();
        apiFlowModule.saveBusinessAnalyseResult();

        blockFlowModule.map();
        blockFlowModule.reduce();
        blockFlowModule.doBusinessAnalyse();
        blockFlowModule.saveBusinessAnalyseResult();

        dailyActivelyUsersModule.map();
        dailyActivelyUsersModule.reduce();
        dailyActivelyUsersModule.doBusinessAnalyse();
        dailyActivelyUsersModule.saveBusinessAnalyseResult();

        dailyAVGOnlineTimeModule.map();
        dailyAVGOnlineTimeModule.reduce();
        dailyAVGOnlineTimeModule.doBusinessAnalyse();
        dailyAVGOnlineTimeModule.saveBusinessAnalyseResult();

        dailyNewUsersModule.map();
        dailyNewUsersModule.reduce();
        dailyNewUsersModule.doBusinessAnalyse();
        dailyNewUsersModule.saveBusinessAnalyseResult();

        dailyRetentionModule.map();
        dailyRetentionModule.reduce();
        dailyRetentionModule.doBusinessAnalyse();
        dailyRetentionModule.saveBusinessAnalyseResult();

        itemFlowModule.map();
        itemFlowModule.reduce();
        itemFlowModule.doBusinessAnalyse();
        itemFlowModule.saveBusinessAnalyseResult();

        moneyFlowModule.map();
        moneyFlowModule.reduce();
        moneyFlowModule.doBusinessAnalyse();
        moneyFlowModule.saveBusinessAnalyseResult();

        payFlowModule.map();
        payFlowModule.reduce();
        payFlowModule.doBusinessAnalyse();
        payFlowModule.saveBusinessAnalyseResult();

        playerBehaviourFlowModule.map();
        playerBehaviourFlowModule.reduce();
        playerBehaviourFlowModule.doBusinessAnalyse();
        playerBehaviourFlowModule.saveBusinessAnalyseResult();

        roundFlowModule.map();
        roundFlowModule.reduce();
        roundFlowModule.doBusinessAnalyse();
        roundFlowModule.saveBusinessAnalyseResult();

        taskFlowModule.map();
        taskFlowModule.reduce();
        taskFlowModule.doBusinessAnalyse();
        taskFlowModule.saveBusinessAnalyseResult();
    }
}
