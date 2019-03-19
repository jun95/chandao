package com.selfboot.chandao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


/**
 * Created by hwj on 2019/3/14.
 */
@SpringBootApplication
@ServletComponentScan //scans from the package of the annotated class (@WebServlet, @WebFilter, and @WebListener)
public class ChanDaoMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChanDaoMainApplication.class,args);
    }
}
