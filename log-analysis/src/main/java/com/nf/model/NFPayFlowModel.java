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
@Table(name = "pay_flow")
public class NFPayFlowModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String plat;

    private String appid;

    private String zoneid;

    private String time;

    //此类类型的成功或者失败
    private Integer number;

    private String pay_item_id;
    private String reason1;
    private String reason2;
    private String reason3;
    private String flowType;
    
    private float avg_online_time;
    private float avg_level;
    private float avg_login_count;
    private float avg_pay_money;
    private float avg_pay_total_money;
    private float avg_pay_count;
    private float avg_status;

}