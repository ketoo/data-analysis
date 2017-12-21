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
@Table(name = "api_flow")
public class NFAPIFlowModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String plat;

    private String appid;

    private String zoneid;

    private String time;

    private Integer total_number;
    private Integer api_type;
    private Integer api_id;
    private float avg_online_time;
    private float avg_level;
    private float avg_login_count;
    private float avg_total_money;
    private float avg_cost;
}
