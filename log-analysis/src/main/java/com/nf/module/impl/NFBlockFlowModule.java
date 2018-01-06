package com.nf.module.impl;

import com.nf.DAO.NFBlockFlowDAO;
import com.nf.DAO.NFTaskFlowDAO;
import com.nf.comm.NFInputType;
import com.nf.comm.NFLogType;
import com.nf.model.NFBlockFlowModel;
import com.nf.module.NFIBlockFlowModule;
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
public class NFBlockFlowModule implements NFIBlockFlowModule
{
    @Autowired
    private NFILogModule logModule;
    
    @Autowired
    private NFTaskFlowDAO taskFlowDAO;
    
    @Autowired
    private NFBlockFlowDAO blockFlowDAO;
    
    private HashMap<String, List<String>> mxArgList = new HashMap<String, List<String>>();
    private HashMap<String, List<String>> mxErrIDList = new HashMap<String, List<String>>();
    
    //for saving
    private static List<NFBlockFlowModel> mxModelList = new ArrayList<>();
    
    @Override
    public void reset()
    {
        mxArgList.clear();
        mxErrIDList.clear();
        mxModelList.clear();
    }
    
    @Override
    public void map()
    {
        List<String> strList = logModule.getLogData(NFLogType.LOG_BLOCK);
        if (strList != null)
        {
            for (int i = 0; i < strList.size(); ++i)
            {
                String line = strList.get(i);
                String[] elements = StringUtils.split(line, '|');
                if (elements.length == NFInputType.BlockFlow.values().length)
                {
                    String errID = elements[NFInputType.BlockFlow.ErrID.getId()];
                    String arg1 = elements[NFInputType.BlockFlow.Arg1.getId()];
                
                    if (!mxArgList.containsKey(arg1))
                    {
                        mxArgList.put(arg1, new ArrayList<>());
                    }
                
                    if (!mxErrIDList.containsKey(errID))
                    {
                        mxErrIDList.put(errID, new ArrayList<>());
                    }
    
                    mxArgList.get(arg1).add(line);
                    mxErrIDList.get(errID).add(line);
                }
            }
        }
    }
    
    @Override
    public void reduce()
    {
    
    }
    @Override
    public void doBusinessAnalyse()
    {
        for (Map.Entry<String, List<String>> entry : mxErrIDList.entrySet())
        {
            String taskType = entry.getKey();
            List<String> lineList = entry.getValue();
        
        
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
            NFBlockFlowModel xModel = new NFBlockFlowModel();
            xModel.setTime(sdf.format(calendar.getTime()));
            xModel.setTotal_number(lineList.size());
            xModel.setError_id(taskType);
            mxModelList.add(xModel);
        }
        
        for (Map.Entry<String, List<String>> entry : mxArgList.entrySet())
        {
            String taskType = entry.getKey();
            List<String> lineList = entry.getValue();
        
        
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
            NFBlockFlowModel xModel = new NFBlockFlowModel();
            xModel.setTime(sdf.format(calendar.getTime()));
            xModel.setTotal_number(lineList.size());
            xModel.setKey_id(taskType);
            mxModelList.add(xModel);
        }
    }

    @Override
    public void saveBusinessAnalyseResult()
    {
        for (int i = 0; i < mxModelList.size(); ++i)
        {
            blockFlowDAO.saveAndFlush(mxModelList.get(i));
        }
    }

  
}
