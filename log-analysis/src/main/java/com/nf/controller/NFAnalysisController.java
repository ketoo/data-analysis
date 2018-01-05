package com.nf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by James on 5/01/18.
 */
public class NFAnalysisController
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @RequestMapping(value = "/dobz", method = RequestMethod.POST)
    public ResponseEntity<?> doAnalysis(@RequestBody String params)
    {
        logger.info("doAnalysis");
        
        
        return new ResponseEntity<>(params, HttpStatus.OK);
    }
}
