package com.nari.jweka.weka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.experiment.InstanceQuery;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

@Component
public class WekaData {

    /**
     * 数据库配置信息
     */
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * 数据源
     */
    private DataSource dataSource;

    private Instances data;

    /**
     * 无参构造器
     */
    public WekaData(){}

    /**
     * 根据路径读取数据，数据类型根据文件后缀确定
     * @param path 数据源路径
     * @throws Exception
     */
    public WekaData(String path) throws Exception {
        this.dataSource = new DataSource(path);
        this.data = this.dataSource.getDataSet();
    }

    /**
     * 根据sql语句获取数据
     * @param table 数据库表格名称
     * @throws Exception
     * @return 数据实体类Instances
     */
    public Instances getDataFromDataSource(String table) throws Exception {
        InstanceQuery instanceQuery = new InstanceQuery();
        instanceQuery.setUsername(username);
        instanceQuery.setPassword(password);
        instanceQuery.setQuery("select * from " + table);
        this.data = instanceQuery.retrieveInstances();
        return this.data;
    }

    /**
     * 通过路径获取数据源
     * @param path 路径
     * @return Instances数据
     * @throws Exception
     */
    public Instances getDataFromFile(String path) throws Exception {
        this.dataSource = new DataSource(path);
        this.data = this.dataSource.getDataSet();
        return this.data;
    }

    /**
     * 根据条件对数据进行过滤
     * @param options 过滤条件
     * @return 过滤后的数据集Instances
     * @throws Exception
     */
    public Instances filteData(String[] options) throws Exception {
        Remove remove = new Remove();
        remove.setOptions(options);
        remove.setInputFormat(data);
        Instances newData = Filter.useFilter(data, remove);
        this.data = newData;
        return newData;
    }

}
