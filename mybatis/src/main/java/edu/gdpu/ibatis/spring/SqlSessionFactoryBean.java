package edu.gdpu.ibatis.spring;

import edu.gdpu.ibatis.mapping.Environment;
import edu.gdpu.ibatis.session.Configuration;
import edu.gdpu.ibatis.session.SqlSession;
import edu.gdpu.ibatis.session.SqlSessionFactory;
import edu.gdpu.ibatis.session.SqlSessionFactoryBuilder;


import javax.sql.DataSource;
import java.util.Map;

/**
 * @author mazebin
 * @date 2021年 03月26日 16:39:41
 */
public class SqlSessionFactoryBean  {

private SqlSession sqlSession;
private SqlSessionFactory sqlSessionFactory;
private Configuration configuration;

    public SqlSessionFactoryBean() {

    }

    private String mapperScanPackageName;

    public Object getProxy() {

       return null;
    }


    public SqlSession getSqlSession() {
        this.setSqlSession();
        return sqlSession;
    }

    public void setSqlSession() {
        this.sqlSession = this.getSqlSessionFactory().openSession();
    }

    public SqlSessionFactory getSqlSessionFactory() {
        this.setSqlSessionFactory();
        return sqlSessionFactory;
    }

    public void setSqlSessionFactory() {
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().builder(configuration, mapperScanPackageName);
    }
    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(DataSource dataSource) {
        this.configuration = new Configuration(new Environment(dataSource));
    }


    public String getMapperScanPackageName() {
        return mapperScanPackageName;
    }

    public void setMapperScanPackageName(String mapperScanPackageName) {
        this.mapperScanPackageName = mapperScanPackageName;
    }
}