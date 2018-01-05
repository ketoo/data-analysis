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
@Table(name = "daily_retention")
public class NFDailyRetentionModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String plat;

    private String appid;

    private String zoneid;

    private String time;

    private Integer day;//几日留存，次日就是2，7日就是7
    
    private Integer total_number;//留存用户数量，分渠道
    
    private float rate;//留存率，分渠道
}
