package com.nf.module;

import com.nf.comm.NFLogType;

import java.util.Calendar;
import java.util.List;

/**
 * Created by lushenghuang on 20/12/17.
 */
public interface NFILogModule extends NFIBAModule
{
    List<String> getLogData(NFLogType type);
    String getLogDate();
    String getNowCalendar();
    
    Calendar getLogCalendar();
    void setLogDate(Calendar cl);
    
    
    void setLogBAProgress(String strProgress);
    String getLogBAProgress();
    
    void setLogBAProgressModule(String moduleName);
    String getLogBAProgressModule();

}
