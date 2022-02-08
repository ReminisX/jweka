package com.nari.jweka;

import com.nari.jweka.weka.WekaData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import weka.core.Instances;

@SpringBootTest
class JwekaApplicationTests {

    @Autowired
    private WekaData wekaData;

    @Test
    void contextLoads() throws Exception {
        Instances data = wekaData.getDataFromDataSource("airline_passengers");
        System.out.println(data);
    }

}
