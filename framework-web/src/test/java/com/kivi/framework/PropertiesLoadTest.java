package com.kivi.framework;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = { ApplicationContext.class } )
public class PropertiesLoadTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test() {

        Resource[] resources;
        try {
            resources = applicationContext.getResources("classpath*:error-mapping*.properties");
            for (Resource r : resources) {
                System.out.println(r.getFilename());
            }
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
