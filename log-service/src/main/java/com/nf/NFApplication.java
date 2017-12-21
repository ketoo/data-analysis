package com.nf;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.Properties;

@EnableScheduling
@SpringBootApplication
public class NFApplication extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer
{

    public static void main(String [] args)
    {

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
