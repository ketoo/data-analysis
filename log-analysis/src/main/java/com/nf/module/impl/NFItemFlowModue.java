package com.nf.module.impl;

import com.nf.comm.NFInputType;
import com.nf.comm.NFLogType;
import com.nf.model.NFAPIFlowModel;
import com.nf.module.NFIItemFlowModule;
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
public class NFItemFlowModue implements NFIItemFlowModule
{
    @Autowired
    private NFILogModule logModule;
    
    private static Map<String, List<String> > mxItemTypeMap = new HashMap<String, List<String> >();
    private static Map<String, List<String> > mxIDMap = new HashMap<String, List<String> >();
    private static Map<String, List<String> > mxReasonMap = new HashMap<String, List<String> >();
    private static Map<String, List<String> > mxMoneyTypeMap = new HashMap<String, List<String> >();
    
    //for saving
    private static List<NFAPIFlowModel> mxModelList = new ArrayList<>();
    
    
    @Override
    public void reset()
    {
        mxItemTypeMap.clear();
        mxIDMap.clear();
        mxReasonMap.clear();
        mxMoneyTypeMap.clear();
    
        mxModelList.clear();
    }


    @Override
    public void doBusinessAnalyse()
    {
        List<String> strList = logModule.getLogData(NFLogType.LOG_API);
        Integer total = strList.size();
    
        for (Map.Entry<String, List<String>> entry : mxTypeMap.entrySet())
        {
            //for type
            NFAPIFlowModel xModel = new NFAPIFlowModel();
        
            xModel.setAppid("");
            xModel.setZoneid("");
            xModel.setPlat("");
            xModel.setNumber(entry.getValue().size());
            xModel.setTotal_number(total);
        
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            xModel.setTime(sdf.format(calendar.getTime()));
        
            xModel.setApi_type(entry.getKey());
            xModel.setApi_id("");
        
            Integer online_time = 0;
            Integer level = 0;
            Integer login_count = 0;
            Integer total_money = 0;
            Integer cost = 0;
        
            for (int i= 0; i < entry.getValue().size(); ++i)
            {
                String[] elements = StringUtils.split(entry.getValue().get(i),'|');
                if (elements.length == NFInputType.APIFlow.values().length)
                {
                    online_time += Integer.parseInt(elements[NFInputType.APIFlow.TotalOnlineTime.getId()]);
                    level += Integer.parseInt(elements[NFInputType.APIFlow.TotalOnlineTime.getId()]);
                    login_count += Integer.parseInt(elements[NFInputType.APIFlow.TotalOnlineTime.getId()]);
                    total_money += Integer.parseInt(elements[NFInputType.APIFlow.TotalOnlineTime.getId()]);
                    cost += Integer.parseInt(elements[NFInputType.APIFlow.TotalOnlineTime.getId()]);
                }
            }
        
            float avg_online_time = online_time / (float)entry.getValue().size();
            float avg_level = level / (float)entry.getValue().size();
            float avg_login_count = login_count / (float)entry.getValue().size();
            float avg_total_money = total_money / (float)entry.getValue().size();
            float avg_cost = cost / (float)entry.getValue().size();
        
        
            xModel.setAvg_online_time(avg_online_time);
            xModel.setAvg_level(avg_level);
            xModel.setAvg_login_count(avg_login_count);
            xModel.setAvg_total_money(avg_total_money);
            xModel.setAvg_cost(avg_cost);
        
            mxModelList.add(xModel);
        }
    
        for (Map.Entry<String, List<String>> entry : mxIDMap.entrySet())
        {
            //for type
            NFAPIFlowModel xModel = new NFAPIFlowModel();
        
            xModel.setAppid("");
            xModel.setZoneid("");
            xModel.setPlat("");
            xModel.setNumber(entry.getValue().size());
            xModel.setTotal_number(total);
        
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            xModel.setTime(sdf.format(calendar.getTime()));
        
            xModel.setApi_type("");
            xModel.setApi_id(entry.getKey());
        
            Integer online_time = 0;
            Integer level = 0;
            Integer login_count = 0;
            Integer total_money = 0;
            Integer cost = 0;
        
            for (int i= 0; i < entry.getValue().size(); ++i)
            {
                String[] elements = StringUtils.split(entry.getValue().get(i),'|');
                if (elements.length == NFInputType.APIFlow.values().length)
                {
                    online_time += Integer.parseInt(elements[NFInputType.APIFlow.TotalOnlineTime.getId()]);
                    level += Integer.parseInt(elements[NFInputType.APIFlow.TotalOnlineTime.getId()]);
                    login_count += Integer.parseInt(elements[NFInputType.APIFlow.TotalOnlineTime.getId()]);
                    total_money += Integer.parseInt(elements[NFInputType.APIFlow.TotalOnlineTime.getId()]);
                    cost += Integer.parseInt(elements[NFInputType.APIFlow.TotalOnlineTime.getId()]);
                }
            }
        
            float avg_online_time = online_time / (float)entry.getValue().size();
            float avg_level = level / (float)entry.getValue().size();
            float avg_login_count = login_count / (float)entry.getValue().size();
            float avg_total_money = total_money / (float)entry.getValue().size();
            float avg_cost = cost / (float)entry.getValue().size();
        
        
            xModel.setAvg_online_time(avg_online_time);
            xModel.setAvg_level(avg_level);
            xModel.setAvg_login_count(avg_login_count);
            xModel.setAvg_total_money(avg_total_money);
            xModel.setAvg_cost(avg_cost);
        
            mxModelList.add(xModel);
        }
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
        List<String> strList = logModule.getLogData(NFLogType.LOG_ITEM);
        for (int i = 0; i < strList.size(); ++i)
        {
            String line = strList.get(i);
            String[] elements = StringUtils.split(line,'|');
            if (elements.length == NFInputType.ItemFlow.values().length)
            {
                //2分析平台
                String itemType = elements[NFInputType.ItemFlow.ItemType.getId()];
                String itemID = elements[NFInputType.ItemFlow.ItemId.getId()];
                String reasonID = elements[NFInputType.ItemFlow.Reason.getId()];
                String moneyType = elements[NFInputType.ItemFlow.MoneyType.getId()];
            
                if (!mxItemTypeMap.containsKey(itemType))
                {
                    mxItemTypeMap.put(itemType, new ArrayList<String>());
                }
            
                if (!mxIDMap.containsKey(itemID))
                {
                    mxIDMap.put(itemID, new ArrayList<String>());
                }
                
                if (!mxReasonMap.containsKey(reasonID))
                {
                    mxReasonMap.put(reasonID, new ArrayList<String>());
                }
                
                if (!mxMoneyTypeMap.containsKey(moneyType))
                {
                    mxMoneyTypeMap.put(moneyType, new ArrayList<String>());
                }
    
                mxItemTypeMap.get(itemType).add(line);
                mxIDMap.get(itemID).add(line);
                mxReasonMap.get(reasonID).add(line);
                mxMoneyTypeMap.get(moneyType).add(line);
            }
        }
    }
}
