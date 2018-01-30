package com.nf;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@EnableScheduling
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages="com.nf.*")
@EnableJpaRepositories(basePackages={"com.spring.app.repository", "com.nf.*"})
public class NFApplication extends SpringBootServletInitializer
{
    static Logger logger = Logger.getLogger(NFApplication.class.getName());
    
    public static void main(String [] args)
    {
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(calendar.getTime()));
    
        calendar.add(Calendar.DAY_OF_YEAR, -27);
        System.out.println(sdf.format(calendar.getTime()));
        
        SpringApplication.run(NFApplication.class, args);
    }

}
