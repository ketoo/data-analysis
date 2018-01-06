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
@Table(name = "daily_new_users")
//分渠道
public class NFDailyNewUsersModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String plat;

    private String appid;

    private String zoneid;

    private String time;
    
    //今天此平台plat的新增
    private Integer number;
    
    //今天所有的新增,不分渠道
    private Integer today_number;

    //历史所有新增,不分渠道
    private Integer total_number;
    
    //历史所有新增,不分渠道
    private Integer total_device;


}
