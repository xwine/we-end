package com.github.xwine.end.test;

import com.github.xwine.end.mock.constraint.IConst;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeEndTestApplication {

    public static void main(String[] args) {
//        System.setProperty(IConst.PROD_DATA_CONFIG_MOCK_ON,"true");
        SpringApplication.run(WeEndTestApplication.class, args);
    }

}
