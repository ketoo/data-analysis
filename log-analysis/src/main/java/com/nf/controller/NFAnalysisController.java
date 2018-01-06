package com.nf.controller;

import com.nf.controller.ResponseBody.ResProgressBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by James on 5/01/18.
 */
@RestController
public class NFAnalysisController
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @RequestMapping(value = "/dobz", method = RequestMethod.POST)
    public ResponseEntity<?> doAnalysis(@RequestBody String date)
    {
        logger.info(date);
        
        //start do analysis with date
        
        return new ResponseEntity<>(date, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/query/{date}", method = RequestMethod.GET)
    public ResponseEntity<?> queryProgress(@PathVariable("date") String date)
    {
        logger.info("doAnalysis");
        
        ResProgressBody xResProgressBody = new ResProgressBody();
        xResProgressBody.setAnalysingModule("");
        xResProgressBody.setDate(date);
        xResProgressBody.setFProgress(0.7f);
        
        return new ResponseEntity<>(xResProgressBody, HttpStatus.OK);
    }
}
