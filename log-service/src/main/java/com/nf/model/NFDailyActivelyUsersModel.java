package com.nf.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by lushenghuang on 20/12/17.
 */

@Component
@Data
@Entity
@Table(name = "daily_actively_users")
//分渠道
public class NFDailyActivelyUsersModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String plat;

    private String appid;

    private String zoneid;

    private String time;
    
    private Integer number;//当前渠道人数
    
    private Integer total_number;//今日的总人数，不分渠道

    private float avg_time;//平均在线时间，不分渠道
}