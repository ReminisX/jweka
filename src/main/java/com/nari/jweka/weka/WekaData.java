package com.nari.jweka.weka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.*;
import weka.experiment.InstanceQuery;

@Component
public class WekaData {

    /**
     * 数据库配置信息
     */
    private static String username;

    private static String password;

    @Autowired
    public WekaData(@Value("${spring.datasource.username}") String username, @Value("${spring.datasource.password}") String password){
        WekaData.username = username;
        WekaData.password = password;
    }

    /**
     * 根据路径读取数据，数据类型根据文件后缀确定
     * @param path 数据源路径
     * @throws Exception
     */
    public static Instances getDataByFile(String path) throws Exception {
        DataSource dataSource = new DataSource(path);
        Instances data = dataSource.getDataSet();
        return data;
    }

    /**
     * 根据sql语句获取数据
     * @param table 数据库表格名称
     * @throws Exception
     * @return 数据实体类Instances
     */
    public static Instances getDataByDataBase(String table) throws Exception {
        InstanceQuery instanceQuery = new InstanceQuery();
        instanceQuery.setUsername(username);
        instanceQuery.setPassword(password);
        instanceQuery.setQuery("select * from " + table);
        return instanceQuery.retrieveInstances();
    }

    /**
     * 数据写入文件
     * @param path arff文件路径
     * @param data 源数据
     * @throws Exception
     */
    public static void instances2Arff(String path, Instances data) throws Exception {
        DataSink.write(path, data);
    }

    public static void instances2DataSource(Instances data){

    }

}
