package com.nf.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by James on 4/01/18.
 */

@Component
@Data
@Entity
@Table(name = "pay_sum_model")
public class NFPaySumModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private String plat;
    
    private String appid;
    
    private String zoneid;
}
