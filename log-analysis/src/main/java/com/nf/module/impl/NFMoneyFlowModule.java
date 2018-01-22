package com.nf.module.impl;

import com.nf.DAO.NFMoneyFlowDAO;
import com.nf.comm.NFInputType;
import com.nf.comm.NFLogType;
import com.nf.model.NFMoneyFlowModel;
import com.nf.module.NFILogModule;
import com.nf.module.NFIMoneyFlowModule;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lushenghuang on 20/12/17.
 */
@Service
public class NFMoneyFlowModule implements NFIMoneyFlowModule
{
    @Autowired
    private NFILogModule logModule;
    
    @Autowired
    private NFMoneyFlowDAO moneyFlowDAO;
    
    static class AddMoney
    {
        public AddMoney()
        {
        
        }
        
        public Map<String, List<String> > mxReasonMap = new HashMap<String, List<String> >();
        public Map<String, List<String> > mxSubReasonMap = new HashMap<String, List<String> >();
        public Map<String, List<String>> mxMoneyTypeMap = new HashMap<String, List<String> >();
    }
    
    
    static class ReduceMoney
    {
        public ReduceMoney()
        {
        
        }
        
        public Map<String, List<String> > mxReasonMap = new HashMap<String, List<String> >();
        public Map<String, List<String> > mxSubReasonMap = new HashMap<String, List<String> >();
        public Map<String, List<String> > mxMoneyTypeMap = new HashMap<String, List<String> >();
    }
    
    private  static AddMoney addItem = new AddMoney();
    private  static ReduceMoney reduceItem = new ReduceMoney();
    //for saving
    private static List<NFMoneyFlowModel> mxModelList = new ArrayList<>();
    
    @Override
    public void reset()
    {
        addItem.mxReasonMap.clear();
        addItem.mxSubReasonMap.clear();
        addItem.mxMoneyTypeMap.clear();
    
        reduceItem.mxReasonMap.clear();
        reduceItem.mxSubReasonMap.clear();
        reduceItem.mxMoneyTypeMap.clear();
    
        mxModelList.clear();
    }
    
    enum MapType
    {
        ReasonMap,
        SubReasonMap,
        MoneyTypeMap,
    }
    
    private void doBusinessAnalyse(String flowType, MapType eType, Map<String, List<String> > map)
    {
        for (Map.Entry<String, List<String>> entry : map.entrySet())
        {
            //for type
            NFMoneyFlowModel xModel = new NFMoneyFlowModel();
            
            xModel.setAppid("");
            xModel.setZoneid("");
            xModel.setPlat("");
          
            
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            xModel.setTime(sdf.format(calendar.getTime()));
    
            xModel.setReason("");
            xModel.setSub_reason("");
            xModel.setMoney_type("");
    
            if (eType == MapType.MoneyTypeMap)
            {
                xModel.setMoney_type(entry.getKey());
            }
            if (eType == MapType.SubReasonMap)
            {
                xModel.setSub_reason(entry.getKey());
            }
            if (eType == MapType.ReasonMap)
            {
                xModel.setReason(entry.getKey());
            }
    
            xModel.setFlow_type(flowType);
            
            Integer level = 0;
            Integer afterMoney = 0;
            Integer money = 0;
            
            for (int i= 0; i < entry.getValue().size(); ++i)
            {
                String[] elements = StringUtils.split(entry.getValue().get(i),'|');
                if (elements.length == NFInputType.MoneyFlow.values().length)
                {
                    level += Integer.parseInt(elements[NFInputType.MoneyFlow.Level.getId()]);
                    afterMoney += Integer.parseInt(elements[NFInputType.MoneyFlow.AfterMoney.getId()]);
                    money += Integer.parseInt(elements[NFInputType.MoneyFlow.Money.getId()]);
                }
            }
            
            xModel.setNumber(money);
            //xModel.setTotal_number(total);
            
            float avg_level = level / (float)entry.getValue().size();
            float avg_after_money = afterMoney / (float)entry.getValue().size();
            float avg_money = money / (float)entry.getValue().size();
            
            xModel.setAvg_level(avg_level);
            xModel.setAvg_after_money(avg_after_money);
            xModel.setAvg_money(avg_money);
            
            mxModelList.add(xModel);
        }
    }
    @Override
    public void doBusinessAnalyse()
    {
        doBusinessAnalyse("0", MapType.ReasonMap, addItem.mxReasonMap);
        doBusinessAnalyse("0", MapType.SubReasonMap, addItem.mxSubReasonMap);
        doBusinessAnalyse("0", MapType.MoneyTypeMap, addItem.mxMoneyTypeMap);
    
        doBusinessAnalyse("1", MapType.ReasonMap, reduceItem.mxReasonMap);
        doBusinessAnalyse("1", MapType.SubReasonMap, reduceItem.mxSubReasonMap);
        doBusinessAnalyse("1", MapType.MoneyTypeMap, reduceItem.mxMoneyTypeMap);
    }

    @Override
    public void saveBusinessAnalyseResult()
    {
        for (int i = 0; i < mxModelList.size(); ++i)
        {
            moneyFlowDAO.saveAndFlush(mxModelList.get(i));
        }
    }

    @Override
    public void map()
    {

    }

    @Override
    public void reduce()
    {
        List<String> strList = logModule.getLogData(NFLogType.LOG_MONEY);
        for (int i = 0; i < strList.size(); ++i)
        {
            String line = strList.get(i);
            String[] elements = StringUtils.split(line,'|');
            if (elements.length == NFInputType.MoneyFlow.values().length)
            {
                //2分析平台
                String addOrReduce = elements[NFInputType.MoneyFlow.AddOrReduce.getId()];
                String reasonID = elements[NFInputType.MoneyFlow.Reason.getId()];
                String subReasonID = elements[NFInputType.MoneyFlow.Reason.getId()];
                String moneyType = elements[NFInputType.MoneyFlow.MoneyType.getId()];
            
                if (addOrReduce == "0")
                {
                
                    if (!addItem.mxReasonMap.containsKey(reasonID))
                    {
                        addItem.mxReasonMap.put(reasonID, new ArrayList<String>());
                    }
                
                    if (!addItem.mxSubReasonMap.containsKey(subReasonID))
                    {
                        addItem.mxSubReasonMap.put(subReasonID, new ArrayList<String>());
                    }
                
                    if (!addItem.mxMoneyTypeMap.containsKey(moneyType))
                    {
                        addItem.mxMoneyTypeMap.put(moneyType, new ArrayList<String>());
                    }
                
                    addItem.mxReasonMap.get(reasonID).add(line);
                    addItem.mxSubReasonMap.get(subReasonID).add(line);
                    addItem.mxMoneyTypeMap.get(moneyType).add(line);
                }
                else
                {
               
                    if (!reduceItem.mxReasonMap.containsKey(reasonID))
                    {
                        reduceItem.mxReasonMap.put(reasonID, new ArrayList<String>());
                    }
                    if (!addItem.mxSubReasonMap.containsKey(subReasonID))
                    {
                        addItem.mxSubReasonMap.put(subReasonID, new ArrayList<String>());
                    }
                    if (!reduceItem.mxMoneyTypeMap.containsKey(moneyType))
                    {
                        reduceItem.mxMoneyTypeMap.put(moneyType, new ArrayList<String>());
                    }
                
                    reduceItem.mxReasonMap.get(reasonID).add(line);
                    reduceItem.mxSubReasonMap.get(subReasonID).add(line);
                    reduceItem.mxMoneyTypeMap.get(moneyType).add(line);
                }
            }
        }
    }
}
