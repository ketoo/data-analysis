package com.nf.module.impl;

import com.nf.DAO.NFTaskFlowDAO;
import com.nf.comm.NFInputType;
import com.nf.comm.NFLogType;
import com.nf.model.NFTaskFlowModel;
import com.nf.module.NFILogModule;
import com.nf.module.NFITaskFlowModule;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lushenghuang on 20/12/17.
 */
@Service
public class NFTaskFlowModule implements NFITaskFlowModule
{
    @Autowired
    private NFILogModule logModule;
    
    @Autowired
    private NFTaskFlowDAO taskFlowDAO;
    
    
    private HashMap<String, List<String>> mxTypeTaskList = new HashMap<String, List<String>>();
    private HashMap<String, List<String>> mxIDTaskList = new HashMap<String, List<String>>();
    
    //for saving
    private static List<NFTaskFlowModel> mxModelList = new ArrayList<>();
    
    @Override
    public void map()
    {
        List<String> strList = logModule.getLogData(NFLogType.LOG_TASK);
        if (strList != null)
        {
            for (int i = 0; i < strList.size(); ++i)
            {
                String line = strList.get(i);
                String[] elements = StringUtils.split(line, '|');
                if (elements.length == NFInputType.TaskFlow.values().length)
                {
                    String taskType = elements[NFInputType.TaskFlow.TaskType.getId()];
                    String taskID = elements[NFInputType.TaskFlow.TaskId.getId()];
    
                    if (!mxTypeTaskList.containsKey(taskType))
                    {
                        mxTypeTaskList.put(taskType, new ArrayList<>());
                    }
                    
                    if (!mxIDTaskList.containsKey(taskID))
                    {
                        mxIDTaskList.put(taskID, new ArrayList<>());
                    }
    
                    mxTypeTaskList.get(taskType).add(line);
                    mxIDTaskList.get(taskID).add(line);
                }
            }
        }
    }
    
    @Override
    public void reduce()
    {
    
    }

    @Override
    public void reset()
    {
        mxTypeTaskList.clear();
        mxIDTaskList.clear();
        mxModelList.clear();
    }

    @Override
    public void doBusinessAnalyse()
    {
        for (Map.Entry<String, List<String>> entry : mxTypeTaskList.entrySet())
        {
            String taskType = entry.getKey();
            List<String> lineList = entry.getValue();
    
    
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
            int nFinished = 0;
            int nStarted = 0;

            for (int i = 0; i < lineList.size(); ++i)
            {
                String line = lineList.get(i);
                String[] elements = StringUtils.split(line, '|');
                if (elements.length == NFInputType.TaskFlow.values().length)
                {
                    String status = elements[NFInputType.TaskFlow.TaskStatus.getId()];
                    Integer count = Integer.parseInt(status);
                    if (count == 1)
                    {
                        nFinished++;
                    }
                    else
                    {
                        nStarted++;
                    }
                }
            }
    
            NFTaskFlowModel xFinishedModel = new NFTaskFlowModel();
            xFinishedModel.setTime(sdf.format(calendar.getTime()));
            xFinishedModel.setTask_type(taskType);
            xFinishedModel.setTask_id("");
            xFinishedModel.setTotal_number(nFinished);
            xFinishedModel.setTask_status(1);
    
            NFTaskFlowModel xStartedModel = new NFTaskFlowModel();
            xStartedModel.setTime(sdf.format(calendar.getTime()));
            xStartedModel.setTask_type(taskType);
            xStartedModel.setTask_id("");
            xStartedModel.setTotal_number(nStarted);
            xStartedModel.setTask_status(0);
   
            mxModelList.add(xStartedModel);
            mxModelList.add(xFinishedModel);
        }
    
    
        for (Map.Entry<String, List<String>> entry : mxIDTaskList.entrySet())
        {
            String taskType = entry.getKey();
            List<String> lineList = entry.getValue();
        
        
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
            int nFinished = 0;
            int nStarted = 0;
        
            for (int i = 0; i < lineList.size(); ++i)
            {
                String line = lineList.get(i);
                String[] elements = StringUtils.split(line, '|');
                if (elements.length == NFInputType.TaskFlow.values().length)
                {
                    String status = elements[NFInputType.TaskFlow.TaskStatus.getId()];
                    Integer count = Integer.parseInt(status);
                    if (count == 1)
                    {
                        nFinished++;
                    }
                    else
                    {
                        nStarted++;
                    }
                }
            }
        
            NFTaskFlowModel xFinishedModel = new NFTaskFlowModel();
            xFinishedModel.setTime(sdf.format(calendar.getTime()));
            xFinishedModel.setTask_type(taskType);
            xFinishedModel.setTask_id("");
            xFinishedModel.setTotal_number(nFinished);
            xFinishedModel.setTask_status(1);
        
            NFTaskFlowModel xStartedModel = new NFTaskFlowModel();
            xStartedModel.setTime(sdf.format(calendar.getTime()));
            xStartedModel.setTask_type(taskType);
            xStartedModel.setTask_id("");
            xStartedModel.setTotal_number(nStarted);
            xStartedModel.setTask_status(0);
        
            mxModelList.add(xStartedModel);
            mxModelList.add(xFinishedModel);
        }
    }

    @Override
    public void saveBusinessAnalyseResult()
    {
        for (int i = 0; i < mxModelList.size(); ++i)
        {
            taskFlowDAO.saveAndFlush(mxModelList.get(i));
        }
    }
}
