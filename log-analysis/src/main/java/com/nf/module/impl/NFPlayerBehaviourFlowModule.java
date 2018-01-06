package com.nf.module.impl;

import com.nf.DAO.NFPlayerBehaviourFlowDAO;
import com.nf.comm.NFInputType;
import com.nf.comm.NFLogType;
import com.nf.model.NFPlayerBehaviourFlowModel;
import com.nf.module.NFILogModule;
import com.nf.module.NFIPlayerBehaviourFlowModule;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lushenghuang on 20/12/17.
 */
@Service
public class NFPlayerBehaviourFlowModule implements NFIPlayerBehaviourFlowModule
{
    @Autowired
    private NFILogModule logModule;
    
    @Autowired
    private NFPlayerBehaviourFlowDAO playerBehaviourFlowDAO;
    
    private HashMap<String, List<String>> mxArgList = new HashMap<String, List<String>>();
    
    //for saving
    private static List<NFPlayerBehaviourFlowModel> mxModelList = new ArrayList<>();
    
    
    @Override
    public void reset()
    {
        mxArgList.clear();
        mxModelList.clear();
    }

    @Override
    public void doBusinessAnalyse()
    {
        for (Map.Entry<String, List<String>> entry : mxArgList.entrySet())
        {
            String taskType = entry.getKey();
            List<String> lineList = entry.getValue();
        
        
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
            NFPlayerBehaviourFlowModel xModel = new NFPlayerBehaviourFlowModel();
            xModel.setTime(sdf.format(calendar.getTime()));
            xModel.setTotal_number(lineList.size());
            
            mxModelList.add(xModel);
        }
    }

    @Override
    public void saveBusinessAnalyseResult()
    {
        for (int i = 0; i < mxModelList.size(); ++i)
        {
            playerBehaviourFlowDAO.saveAndFlush(mxModelList.get(i));
        }
    }

    @Override
    public void map()
    {
        List<String> strList = logModule.getLogData(NFLogType.LOG_BEHAVIOUR);
        if (strList != null)
        {
            for (int i = 0; i < strList.size(); ++i)
            {
                String line = strList.get(i);
                String[] elements = StringUtils.split(line, '|');
                if (elements.length == NFInputType.PlayerBehaviourFlow.values().length)
                {
                    String taskType = elements[NFInputType.PlayerBehaviourFlow.BehaviourId.getId()];
                
                    if (!mxArgList.containsKey(taskType))
                    {
                        mxArgList.put(taskType, new ArrayList<>());
                    }
    
                    mxArgList.get(taskType).add(line);
                }
            }
        }
    }

    @Override
    public void reduce()
    {
    
    }
}
