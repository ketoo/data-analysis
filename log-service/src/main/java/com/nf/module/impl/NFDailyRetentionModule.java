package com.nf.module.impl;

import com.nf.module.NFIDailyRetentionModule;
import com.nf.module.NFILogModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lushenghuang on 20/12/17.
 */
@Service
public class NFDailyRetentionModule implements NFIDailyRetentionModule
{
    @Autowired
    private NFILogModule logModule;

    @Override
    public void reset()
    {

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

    }

    @Override
    public void reduce()
    {

    }
}
