package cz.muni.ics.serviceslist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "cz.muni.ics")
public class ServicesListApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ServicesListApplication.class, args);
    }

}
