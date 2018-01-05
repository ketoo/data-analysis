package com.nf.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by lushenghuang on 20/12/17.
 */

@Component
@Data
@Entity
@Table(name = "round_flow")
public class NFRoundFlowModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String plat;

    private String appid;

    private String zoneid;

    private String time;

    private Integer total_number;

    private Integer battle_id;

    private Integer battle_type;

    private float avg_fight_value;
    private float avg_level;
    private float avg_round_time;
    private float avg_mid_round_time;
    private float avg_dead_count;
    private float avg_result;


}