package com.nf.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by lushenghuang on 20/12/17.
 */

@Data
@Entity
@Table(name = "daily_avg_onlinetime")
//不分渠道
public class NFDailyAVGOnlineTimeModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String plat;

    private String appid;

    private String zoneid;

    private String time;
    
    private Integer total_number;//今日的总人数，不分渠道
    
    private float avg_time;//平均时间，不分渠道
    
    private float avg_level;//平均等级，不分渠道
    
}
