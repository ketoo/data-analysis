package com.nf;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

@EnableScheduling
@SpringBootApplication
public class NFApplication extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer
{

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

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container)
    {
        container.setPort(5000);

        Properties props = new Properties();
        try
        {
            props.load(getClass().getClassLoader().getResourceAsStream("/log4j.properties"));
            PropertyConfigurator.configure(props);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
