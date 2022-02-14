package com.nari.jweka.weka;

import org.springframework.stereotype.Component;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

@Component
public class WekaFilter {

    /**
     * 根据条件对数据进行过滤
     * @param data 过滤数据
     * @param options 过滤条件
     * @return 过滤后的数据集Instances
     * @throws Exception
     */
    public static Instances filteData(Instances data, String[] options) throws Exception {
        Remove remove = new Remove();
        remove.setOptions(options);
        remove.setInputFormat(data);
        Instances newData = Filter.useFilter(data, remove);
        return newData;
    }

    /**
     * 根据条件对数据进行过滤
     * @param data 源数据
     * @param option 过滤条件
     * @return 过滤后的数据集Instances
     * @throws Exception
     */
    public static Instances filteData(Instances data, String option) throws Exception {
        String[] options = weka.core.Utils.splitOptions(option);
        return filteData(data, options);
    }

}
