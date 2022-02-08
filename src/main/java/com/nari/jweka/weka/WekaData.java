package com.nari.jweka.weka;

import org.springframework.beans.factory.annotation.Value;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.experiment.InstanceQuery;

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
     * @param sql
     * @throws Exception
     */
    public void getDataFromDataSource(String sql) throws Exception {
        InstanceQuery instanceQuery = new InstanceQuery();
        instanceQuery.setUsername(username);
        instanceQuery.setPassword(password);
        instanceQuery.setQuery(sql);
        this.data = instanceQuery.retrieveInstances();
    }



}
