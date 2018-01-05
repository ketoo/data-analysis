package com.nf.module.impl;

import com.nf.module.NFILogModule;
import com.nf.module.NFIPlayerBehaviourFlowModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lushenghuang on 20/12/17.
 */
@Service
public class NFPlayerBehaviourFlowModule implements NFIPlayerBehaviourFlowModule
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
