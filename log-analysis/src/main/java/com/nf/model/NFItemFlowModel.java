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
@Table(name = "item_flow")
public class NFItemFlowModel
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
    private Integer item_type;
    private String item_id;
    private float avg_count;
    private Integer reason;
    private Integer sub_reason;
    private Integer money_type;
    private float avg_money;
}
