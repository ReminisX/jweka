package com.nari.jweka;

import com.nari.jweka.weka.WekaClassify;
import com.nari.jweka.weka.WekaData;
import com.nari.jweka.weka.WekaFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import weka.classifiers.trees.J48;
import weka.core.Instances;

@SpringBootTest
class JwekaApplicationTests {

    @Autowired
    private WekaFilter wekaFilter;

    @Test
    void contextLoads() throws Exception {
        String projectPath = "D:\\WorkSpace\\jweka\\";
        Instances data = WekaData.getDataByFile(projectPath + "files\\weather.nominal.arff");
        J48 tree = WekaClassify.classifyByJ48(data, "-C 0.25 -M 2");
        System.out.println(tree);
    }

}
