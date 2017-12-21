package com.nf.module;

/**
 * Created by lushenghuang on 20/12/17.
 */
public interface NFIBAModule
{
    void reset();

    void map();
    void reduce();

    void doBusinessAnalyse();
    void saveBusinessAnalyseResult();

}
