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
@Table(name = "money_flow")
public class NFMoneyFlowModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String plat;

    private String appid;

    private String zoneid;

    private String time;
    
    private Integer total_number;
    private Integer number;

    private String reason;
    private String sub_reason;
    private String flow_type;
    private String money_type;
    
    private float avg_level;
    private float avg_after_money;
    private float avg_money;
}
