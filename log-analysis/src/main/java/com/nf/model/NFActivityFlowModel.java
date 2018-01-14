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
@Table(name = "activity_flow")
public class NFActivityFlowModel
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

    private float avg_level;

    private Integer active_type;
    private Integer active_id;
    private float avg_count;
    private float avg_status;
}
