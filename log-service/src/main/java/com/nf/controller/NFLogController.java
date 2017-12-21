package com.nf.controller;

import com.nf.module.NFICnfModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lushenghuang on 19/12/17.
 */

@RestController
public class NFLogController
{
    @Autowired
    NFICnfModule cnfModule;


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/addlog", method = RequestMethod.POST)
    public ResponseEntity<?> addLog(@RequestBody String params)
    {
        logger.debug(params);
        logger.info(params);
        logger.error(params);
        return new ResponseEntity<>(params, HttpStatus.OK);
    }
}
