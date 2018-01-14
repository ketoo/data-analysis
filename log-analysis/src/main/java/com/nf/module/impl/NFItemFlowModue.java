package com.nf.module.impl;

import com.nf.DAO.NFItemFlowDAO;
import com.nf.comm.NFInputType;
import com.nf.comm.NFLogType;
import com.nf.model.NFItemFlowModel;
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
    
    @Autowired
    private NFItemFlowDAO itemFlowDAO;
    
    static class AddItem
    {
        public AddItem()
        {
        
        }
        
        public Map<String, List<String> > mxItemTypeMap = new HashMap<String, List<String> >();
        public Map<String, List<String> > mxIDMap = new HashMap<String, List<String> >();
        public Map<String, List<String> > mxReasonMap = new HashMap<String, List<String> >();
        public Map<String, List<String> > mxSubReasonMap = new HashMap<String, List<String> >();
        public Map<String, List<String> > mxMoneyTypeMap = new HashMap<String, List<String> >();
    }
    
    
    static class ReduceItem
    {
        public ReduceItem()
        {
        
        }
        
        public Map<String, List<String> > mxItemTypeMap = new HashMap<String, List<String> >();
        public Map<String, List<String> > mxIDMap = new HashMap<String, List<String> >();
        public Map<String, List<String> > mxReasonMap = new HashMap<String, List<String> >();
        public Map<String, List<String> > mxSubReasonMap = new HashMap<String, List<String> >();
        public Map<String, List<String> > mxMoneyTypeMap = new HashMap<String, List<String> >();
    }
    
    private  static AddItem addItem = new AddItem();
    private  static ReduceItem reduceItem = new ReduceItem();
    
    
    //for saving
    private static List<NFItemFlowModel> mxModelList = new ArrayList<>();
    
    
    @Override
    public void reset()
    {
        addItem.mxItemTypeMap.clear();
        addItem.mxIDMap.clear();
        addItem.mxReasonMap.clear();
        addItem.mxSubReasonMap.clear();
        addItem.mxMoneyTypeMap.clear();
    
        reduceItem.mxItemTypeMap.clear();
        reduceItem.mxIDMap.clear();
        reduceItem.mxReasonMap.clear();
        reduceItem.mxSubReasonMap.clear();
        reduceItem.mxMoneyTypeMap.clear();
        
        mxModelList.clear();
    }


    private void doBusinessAnalyse(String flowType, Map<String, List<String> > map)
    {
        List<String> strList = logModule.getLogData(NFLogType.LOG_API);
        Integer total = strList.size();
    
        for (Map.Entry<String, List<String>> entry : map.entrySet())
        {
            //for type
            NFItemFlowModel xModel = new NFItemFlowModel();
        
            xModel.setAppid("");
            xModel.setZoneid("");
            xModel.setPlat("");
            xModel.setNumber(entry.getValue().size());
            xModel.setTotal_number(total);
        
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            xModel.setTime(sdf.format(calendar.getTime()));
        
            xModel.setItem_type(entry.getKey());
            xModel.setItem_id("");
            xModel.setReason("");
            xModel.setSub_reason("");
            xModel.setMoney_type("");
            xModel.setFlow_type(flowType);
    
            Integer level = 0;
            Integer count = 0;
            Integer money = 0;
        
            for (int i= 0; i < entry.getValue().size(); ++i)
            {
                String[] elements = StringUtils.split(entry.getValue().get(i),'|');
                if (elements.length == NFInputType.ItemFlow.values().length)
                {
                    level += Integer.parseInt(elements[NFInputType.ItemFlow.Level.getId()]);
                    count += Integer.parseInt(elements[NFInputType.ItemFlow.Count.getId()]);
                    money += Integer.parseInt(elements[NFInputType.ItemFlow.Money.getId()]);
                }
            }
        
            float avg_level = level / (float)entry.getValue().size();
            float avg_count = count / (float)entry.getValue().size();
            float avg_money = money / (float)entry.getValue().size();
    
            xModel.setAvg_level(avg_level);
            xModel.setAvg_count(avg_count);
            xModel.setAvg_money(avg_money);
        
            mxModelList.add(xModel);
        }
    }
    
    @Override
    public void doBusinessAnalyse()
    {
        doBusinessAnalyse("0", addItem.mxItemTypeMap);
        doBusinessAnalyse("0", addItem.mxIDMap);
        doBusinessAnalyse("0", addItem.mxReasonMap);
        doBusinessAnalyse("0", addItem.mxSubReasonMap);
        doBusinessAnalyse("0", addItem.mxMoneyTypeMap);
    
        doBusinessAnalyse("1", reduceItem.mxItemTypeMap);
        doBusinessAnalyse("1", reduceItem.mxIDMap);
        doBusinessAnalyse("1", reduceItem.mxReasonMap);
        doBusinessAnalyse("1", reduceItem.mxSubReasonMap);
        doBusinessAnalyse("1", reduceItem.mxMoneyTypeMap);
        
    }

    @Override
    public void saveBusinessAnalyseResult()
    {
        for (int i = 0; i < mxModelList.size(); ++i)
        {
            itemFlowDAO.saveAndFlush(mxModelList.get(i));
        }
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
                String addOrReduce = elements[NFInputType.ItemFlow.AddOrReduce.getId()];
                String itemType = elements[NFInputType.ItemFlow.ItemType.getId()];
                String itemID = elements[NFInputType.ItemFlow.ItemId.getId()];
                String reasonID = elements[NFInputType.ItemFlow.Reason.getId()];
                String subReasonID = elements[NFInputType.ItemFlow.Reason.getId()];
                String moneyType = elements[NFInputType.ItemFlow.MoneyType.getId()];
                
                if (addOrReduce == "0")
                {
                    if (!addItem.mxItemTypeMap.containsKey(itemType))
                    {
                        addItem.mxItemTypeMap.put(itemType, new ArrayList<String>());
                    }
    
                    if (!addItem.mxIDMap.containsKey(itemID))
                    {
                        addItem.mxIDMap.put(itemID, new ArrayList<String>());
                    }
    
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
    
                    addItem.mxItemTypeMap.get(itemType).add(line);
                    addItem.mxIDMap.get(itemID).add(line);
                    addItem.mxReasonMap.get(reasonID).add(line);
                    addItem.mxSubReasonMap.get(subReasonID).add(line);
                    addItem.mxMoneyTypeMap.get(moneyType).add(line);
                }
                else
                {
                    if (!reduceItem.mxItemTypeMap.containsKey(itemType))
                    {
                        reduceItem.mxItemTypeMap.put(itemType, new ArrayList<String>());
                    }
    
                    if (!reduceItem.mxIDMap.containsKey(itemID))
                    {
                        reduceItem.mxIDMap.put(itemID, new ArrayList<String>());
                    }
    
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
    
                    reduceItem.mxItemTypeMap.get(itemType).add(line);
                    reduceItem.mxIDMap.get(itemID).add(line);
                    reduceItem.mxReasonMap.get(reasonID).add(line);
                    reduceItem.mxSubReasonMap.get(subReasonID).add(line);
                    reduceItem.mxMoneyTypeMap.get(moneyType).add(line);
                }
            }
        }
    }
}
