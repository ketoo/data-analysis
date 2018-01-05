package com.nf.module;

import com.nf.comm.NFLogType;

import java.util.List;

/**
 * Created by lushenghuang on 20/12/17.
 */
public interface NFILogModule extends NFIBAModule
{
    List<String> getLogData(NFLogType type);
}
