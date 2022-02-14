package com.nari.jweka.weka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import weka.classifiers.trees.J48;
import weka.core.Instances;

@Component
public class WekaClassify {

    private static final Logger logger = LoggerFactory.getLogger(WekaClassify.class);

    /**
     * J48分类器
     * @param data 待训练数据集
     * @param options 条件
     */
    public static J48 classifyByJ48(Instances data, String[] options) throws Exception {
        // 若data未指定分类依据，则默认以最后一列为依据
        if (data.classIndex() == -1){
            logger.warn("未指定分类属性，默认以最后一列为准");
            data.setClassIndex(data.numAttributes()-1);
        }
        J48 tree = new J48();
        tree.setOptions(options);
        tree.buildClassifier(data);
        return tree;
    }

    public static J48 classifyByJ48(Instances data, String option) throws Exception {
        String[] options = weka.core.Utils.splitOptions(option);
        return classifyByJ48(data, options);
    }

}
