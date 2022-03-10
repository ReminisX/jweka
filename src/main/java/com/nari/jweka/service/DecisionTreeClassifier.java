package com.nari.jweka.service;

import com.nari.jweka.utils.GraphViz;
import com.nari.jweka.weka.WekaClassify;
import com.nari.jweka.weka.WekaCluster;
import com.nari.jweka.weka.WekaData;
import com.nari.jweka.weka.WekaFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import weka.classifiers.trees.J48;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

@Component
public class DecisionTreeClassifier implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(DecisionTreeClassifier.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("J48决策树任务开始");
        Instances instances = WekaData.getDataByFile("D:\\WorkSpace\\jweka\\files\\weather.nominal.arff");
        System.out.println("==========原始数据集==========");
        System.out.println(instances);
        Instances[] ins = WekaFilter.stratifiedRemoveFolds(instances);
        Instances train = ins[0];
        Instances test = ins[1];
        System.out.println("==========train数据集==========");
        System.out.println(train);
        System.out.println("==========test数据集==========");
        System.out.println(test);

        String[] options = new String[4];
        options[0] = "-C";
        options[1] = "0.25";
        options[2] = "-M";
        options[3] = "2";
        J48 tree = WekaClassify.classifyByJ48(train, options);
        System.out.println("==========决策树==========");
        System.out.println(tree);
        System.out.println("==========测试集验证决策树==========");
        for (int i = 0; i < test.size(); i++) {
            double res = tree.classifyInstance(test.instance(i));
            System.out.println("第" + i + "次数据分类结果: " + res);
        }
        System.out.println("==========决策树可视化==========");
        String view = tree.graph();
        System.out.println(view);
        System.out.println("==========决策树存储为dot==========");
        File out = new File("D:\\WorkSpace\\jweka\\files\\J48.dot");
        FileWriter fw = new FileWriter(out);
        fw.write(view);
        fw.close();
        System.out.println("==========dot转储为图像==========");
        try {
            Process pro = Runtime.getRuntime().exec("dot D:\\WorkSpace\\jweka\\files\\J48.dot -T pdf -o D:\\WorkSpace\\jweka\\files\\J48.pdf");
            String line;
            BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            while ((line = buf.readLine()) != null)
                System.out.println(line);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info("J48决策树任务结束");
    }
}
