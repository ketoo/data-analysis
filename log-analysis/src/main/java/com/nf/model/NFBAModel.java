package com.nf.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by lushenghuang on 20/12/17.
 */

@Component
@Data
@Entity
public class NFBAModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //@Column(name = "user_name")
    private String plat;

    private String appid;

    private String zoneid;

    private String time;
}
