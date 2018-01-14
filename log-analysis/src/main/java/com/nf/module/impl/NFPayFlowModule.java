package com.nf.module.impl;

import com.nf.module.NFILogModule;
import com.nf.module.NFIPayFlowModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lushenghuang on 20/12/17.
 */
@Service
public class NFPayFlowModule implements NFIPayFlowModule
{
    @Autowired
    private NFILogModule logModule;
    
    //成功的
    static class AddMoney
    {
        public AddMoney()
        {
        
        }
        
        public Map<String, List<String>> mxItemTypeMap = new HashMap<String, List<String> >();
        public Map<String, List<String> > mxIDMap = new HashMap<String, List<String> >();
        public Map<String, List<String> > mxReasonMap = new HashMap<String, List<String> >();
        public Map<String, List<String> > mxSubReasonMap = new HashMap<String, List<String> >();
        public Map<String, List<String>> mxMoneyTypeMap = new HashMap<String, List<String> >();
    }
    
    //失败的
    static class ReduceMoney
    {
        public ReduceMoney()
        {
        
        }
        
        public Map<String, List<String>> mxItemTypeMap = new HashMap<String, List<String> >();
        public Map<String, List<String> > mxIDMap = new HashMap<String, List<String> >();
        public Map<String, List<String> > mxReasonMap = new HashMap<String, List<String> >();
        public Map<String, List<String> > mxSubReasonMap = new HashMap<String, List<String> >();
        public Map<String, List<String>> mxMoneyTypeMap = new HashMap<String, List<String> >();
    }
    
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
