package com.nf.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

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

    private float avg_level;
    private float avg_after_money;
    private float avg_money;
    private Integer reason;
    private Integer sub_reason;
    private Integer flow_type;
    private Integer money_type;
}
