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
@Table(name = "player_behaviour_flow")
public class NFPlayerBehaviourFlowModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String plat;

    private String appid;

    private String zoneid;

    private String time;

    private Integer total_number;
    private Integer behaviour_type;
    private float avg_level;
}
