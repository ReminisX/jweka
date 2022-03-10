package com.nari.jweka.weka;

import org.springframework.stereotype.Component;
import weka.core.Instances;
import weka.core.Option;
import weka.filters.Filter;
import weka.filters.supervised.instance.StratifiedRemoveFolds;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.instance.RemovePercentage;
import weka.filters.unsupervised.instance.RemoveWithValues;

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
        return Filter.useFilter(data, remove);
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

    /**
     * 移除指定列
     * @param data 数据源
     * @param col 被移除的数据列
     * @return 新的数据集
     * @throws Exception
     */
    public static Instances removeCol(Instances data, int col) throws Exception {
        return filteData(data, "-R " + col);
    }

    /**
     * 移除前percent%的数据
     * @param data 数据源
     * @param percent 移除数据的百分比
     * @return 新数据集
     * @throws Exception
     */
    public static Instances removePercenage(Instances data, int percent) throws Exception {
        RemovePercentage removePercentage = new RemovePercentage();
        removePercentage.setOptions(new String[]{"-P", String.valueOf(percent)});
        return Filter.useFilter(data, removePercentage);
    }

    public static Instances reverse(Instances data) throws Exception {
        RemoveWithValues remove = new RemoveWithValues();
        remove.setInvertSelection(true); //　选择未选择（另一部分）的样本
        remove.setInputFormat(data);
        return Filter.useFilter(data, remove);
    }

    /**
     * 分割数据训练集和测试集
     * @param data
     * @return size为2的数组，第一个为训练集，第二个为测试集
     * @throws Exception
     */
    public static Instances[] stratifiedRemoveFolds(Instances data) throws Exception {
        // Set class to last attribute
        if (data.classIndex() == -1){
            data.setClassIndex(data.numAttributes() - 1);
        }
        // use StratifiedRemoveFolds to randomly split the data
        StratifiedRemoveFolds filter = new StratifiedRemoveFolds();

        // set options for creating the subset of data
        String[] options = new String[6];

        options[0] = "-N";                 // indicate we want to set the number of folds
        options[1] = Integer.toString(5);  // split the data into five random folds
        options[2] = "-F";                 // indicate we want to select a specific fold
        options[3] = Integer.toString(1);  // select the first fold
        options[4] = "-S";                 // indicate we want to set the random seed
        options[5] = Integer.toString(1);  // set the random seed to 1

        filter.setOptions(options);        // set the filter options
        filter.setInputFormat(data);       // prepare the filter for the data format
        filter.setInvertSelection(false);  // do not invert the selection

        // apply filter for test data here
        Instances test = Filter.useFilter(data, filter);

        //  prepare and apply filter for training data here
        filter.setInvertSelection(true);     // invert the selection to get other data
        Instances train = Filter.useFilter(data, filter);

        return new Instances[]{train, test};
    }

}
