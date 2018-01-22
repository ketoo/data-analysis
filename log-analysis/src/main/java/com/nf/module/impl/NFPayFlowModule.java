package com.nf.module.impl;

import com.nf.DAO.NFMoneyFlowDAO;
import com.nf.DAO.NFPayFlowDAO;
import com.nf.comm.NFInputType;
import com.nf.comm.NFLogType;
import com.nf.model.NFPayFlowModel;
import com.nf.module.NFILogModule;
import com.nf.module.NFIPayFlowModule;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lushenghuang on 20/12/17.
 */
@Service
public class NFPayFlowModule implements NFIPayFlowModule
{
    @Autowired
    private NFILogModule logModule;
    @Autowired
    private NFPayFlowDAO payFlowDAO;
    //成功的
    static class AddMoney
    {
        public AddMoney()
        {
        
        }
        
        public Map<String, List<String>> mxItemIDMap = new HashMap<String, List<String> >();
        public Map<String, List<String>> mxReason1Map = new HashMap<String, List<String> >();
        public Map<String, List<String>> mxReason2Map = new HashMap<String, List<String> >();
        public Map<String, List<String>> mxReason3Map = new HashMap<String, List<String> >();
    }
    
    //失败的
    static class ReduceMoney
    {
        public ReduceMoney()
        {
        
        }
    
        public Map<String, List<String>> mxItemIDMap = new HashMap<String, List<String> >();
        public Map<String, List<String>> mxReason1Map = new HashMap<String, List<String> >();
        public Map<String, List<String>> mxReason2Map = new HashMap<String, List<String> >();
        public Map<String, List<String>> mxReason3Map = new HashMap<String, List<String> >();
    }
    
    private  static AddMoney addItem = new AddMoney();
    private  static ReduceMoney reduceItem = new ReduceMoney();
    //for saving
    private static List<NFPayFlowModel> mxModelList = new ArrayList<>();
    
    @Override
    public void reset()
    {
        addItem.mxItemIDMap.clear();
        addItem.mxReason1Map.clear();
        addItem.mxReason2Map.clear();
        addItem.mxReason3Map.clear();
    
        reduceItem.mxItemIDMap.clear();
        reduceItem.mxReason1Map.clear();
        reduceItem.mxReason2Map.clear();
        reduceItem.mxReason3Map.clear();
    
        mxModelList.clear();
    }
    
    enum MapType
    {
        ItemMap,
        Reason1Map,
        Reason2Map,
        Reason3Map,
    }
    /*
    private String pay_item_id;

    private String flowType;
    
    private float avg_online_time;
    private float avg_level;
    private float avg_login_count;
    private float avg_pay_money;
    private float avg_pay_total_money;
    private float avg_pay_count;
    private float avg_status;
    */
    private void doBusinessAnalyse(String flowType, MapType eType, Map<String, List<String> > map)
    {
        
        for (Map.Entry<String, List<String>> entry : map.entrySet())
        {
            //for type
            NFPayFlowModel xModel = new NFPayFlowModel();
            xModel.setAppid("");
            xModel.setZoneid("");
            xModel.setPlat("");
            
            
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            xModel.setTime(sdf.format(calendar.getTime()));
    
            xModel.setNumber(entry.getValue().size());
    
            xModel.setPay_item_id("");
            xModel.setReason1("");
            xModel.setReason2("");
            xModel.setReason3("");
            xModel.setFlowType(flowType);
    
            if (eType == MapType.ItemMap)
            {
                xModel.setPay_item_id(entry.getKey());
            }
            if (eType == MapType.Reason1Map)
            {
                xModel.setReason1(entry.getKey());
            }
            if (eType == MapType.Reason2Map)
            {
                xModel.setReason2(entry.getKey());
            }
            if (eType == MapType.Reason3Map)
            {
                xModel.setReason3(entry.getKey());
            }
    
            Integer online_time;
            Integer level;
            Integer login_count;
            Integer pay_money;
            Integer pay_total_money;
            Integer pay_count;
            for (int i= 0; i < entry.getValue().size(); ++i)
            {
                String[] elements = StringUtils.split(entry.getValue().get(i),'|');
                if (elements.length == NFInputType.PayFlow.values().length)
                {
                    online_time += Integer.parseInt(elements[NFInputType.PayFlow.TotalOnlineTime.getId()]);
                    level += Integer.parseInt(elements[NFInputType.PayFlow.Level.getId()]);
                    login_count += Integer.parseInt(elements[NFInputType.PayFlow.LoginCount.getId()]);
                    pay_money += Integer.parseInt(elements[NFInputType.PayFlow.Money.getId()]);
                    pay_total_money += Integer.parseInt(elements[NFInputType.PayFlow.TotalMoney.getId()]);
                    pay_count += Integer.parseInt(elements[NFInputType.PayFlow.PayCount.getId()]);
                }
            }
            
            float avg_online_time = online_time / (float)entry.getValue().size();
            float avg_level = level / (float)entry.getValue().size();
            float avg_login_count = login_count / (float)entry.getValue().size();
            float avg_pay_money = pay_money / (float)entry.getValue().size();
            float avg_pay_total_money = pay_total_money / (float)entry.getValue().size();
            float avg_pay_count = pay_count / (float)entry.getValue().size();
    
    
            xModel.setAvg_online_time(avg_online_time);
            xModel.setAvg_level(avg_level);
            xModel.setAvg_login_count(avg_login_count);
            xModel.setAvg_pay_money(avg_pay_money);
            xModel.setAvg_pay_total_money(avg_pay_total_money);
            xModel.setAvg_pay_total_money(avg_pay_money);
            xModel.setAvg_pay_count(avg_pay_count);
            
            mxModelList.add(xModel);
        }
    }
    @Override
    public void doBusinessAnalyse()
    {
        doBusinessAnalyse("1", MapType.ItemMap, addItem.mxItemIDMap);
        doBusinessAnalyse("1", MapType.Reason1Map, addItem.mxReason1Map);
        doBusinessAnalyse("1", MapType.Reason2Map, addItem.mxReason2Map);
        doBusinessAnalyse("1", MapType.Reason3Map, addItem.mxReason3Map);
    
        doBusinessAnalyse("0", MapType.ItemMap, reduceItem.mxItemIDMap);
        doBusinessAnalyse("0", MapType.Reason1Map, reduceItem.mxReason1Map);
        doBusinessAnalyse("0", MapType.Reason2Map, reduceItem.mxReason2Map);
        doBusinessAnalyse("0", MapType.Reason3Map, reduceItem.mxReason3Map);
    }

    @Override
    public void saveBusinessAnalyseResult()
    {
        for (int i = 0; i < mxModelList.size(); ++i)
        {
            payFlowDAO.saveAndFlush(mxModelList.get(i));
        }
    }

    @Override
    public void map()
    {
    
    }

    @Override
    public void reduce()
    {
        List<String> strList = logModule.getLogData(NFLogType.LOG_PAY);
        if (strList != null)
        {
            for (int i = 0; i < strList.size(); ++i)
            {
                String line = strList.get(i);
                String[] elements = StringUtils.split(line, '|');
                if (elements.length == NFInputType.BlockFlow.values().length)
                {
                    String itemID = elements[NFInputType.PayFlow.ItemID.getId()];
                    String arg1 = elements[NFInputType.PayFlow.Arg1.getId()];
                    String arg2 = elements[NFInputType.PayFlow.Arg2.getId()];
                    String arg3 = elements[NFInputType.PayFlow.Arg3.getId()];
                
                    String state = elements[NFInputType.PayFlow.Status.getId()];
                    if (state == "1")
                    {
                        //success
                        if (!addItem.mxItemIDMap.containsKey(itemID))
                        {
                            addItem.mxItemIDMap.put(itemID, new ArrayList<>());
                        }
                    
                        if (!addItem.mxReason1Map.containsKey(arg1))
                        {
                            addItem.mxReason1Map.put(arg1, new ArrayList<>());
                        }
                    
                        if (!addItem.mxReason2Map.containsKey(arg2))
                        {
                            addItem.mxReason2Map.put(arg2, new ArrayList<>());
                        }
                    
                        if (!addItem.mxReason3Map.containsKey(arg3))
                        {
                            addItem.mxReason3Map.put(arg3, new ArrayList<>());
                        }
                    
                        addItem.mxItemIDMap.get(itemID).add(line);
                        addItem.mxReason1Map.get(arg1).add(line);
                        addItem.mxReason2Map.get(arg2).add(line);
                        addItem.mxReason3Map.get(arg3).add(line);
                    }
                    else
                    {
                        if (!reduceItem.mxItemIDMap.containsKey(itemID))
                        {
                            reduceItem.mxItemIDMap.put(itemID, new ArrayList<>());
                        }
                    
                        if (!reduceItem.mxReason1Map.containsKey(arg1))
                        {
                            reduceItem.mxReason1Map.put(arg1, new ArrayList<>());
                        }
                    
                        if (!reduceItem.mxReason2Map.containsKey(arg2))
                        {
                            reduceItem.mxReason2Map.put(arg2, new ArrayList<>());
                        }
                    
                        if (!reduceItem.mxReason3Map.containsKey(arg3))
                        {
                            reduceItem.mxReason3Map.put(arg3, new ArrayList<>());
                        }
                    
                        reduceItem.mxItemIDMap.get(itemID).add(line);
                        reduceItem.mxReason1Map.get(arg1).add(line);
                        reduceItem.mxReason2Map.get(arg2).add(line);
                        reduceItem.mxReason3Map.get(arg3).add(line);
                    }
                }
            }
        }
    }
}
