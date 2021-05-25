package edu.gdpu.springboot.autoConfiguration;

import com.alibaba.druid.pool.DruidDataSource;
import edu.gdpu.ibatis.mapping.Environment;
import edu.gdpu.ibatis.session.Configuration;
import edu.gdpu.ibatis.session.SqlSession;
import edu.gdpu.ibatis.session.SqlSessionFactory;
import edu.gdpu.ibatis.session.SqlSessionFactoryBuilder;
import edu.gdpu.ibatis.spring.MapperScanRegistrar;
import edu.gdpu.spring.context.annotation.Bean;
import edu.gdpu.spring.context.annotation.PropertiesSource;
import edu.gdpu.spring.context.annotation.Value;

import javax.sql.DataSource;

/**
 * @author mazebin
 * @date 2021年 05月10日 16:16:39
 */
@edu.gdpu.spring.context.annotation.Configuration
@PropertiesSource
public class DruidDataSourceConfig {
    @Value("#{mysql.url}")
    private String url = "jdbc:mysql://localhost:3306/setest?serverTimezone=UTC";
    @Value("#{mysql.username}")
    private String username ="root";
    @Value("#{mysql.password}")
    private String password = "18050910ads";
    @Value("#{mysql.driverClassName}")
    private String driverClassName ="com.mysql.cj.jdbc.Driver";
    @Value("#{mybatis.mapperPackageName}")
    private String mapperPackageName ="cn.binboy.mapper";
    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);

        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
    @Bean
    public Environment environment(){
        DataSource dataSource = dataSource();
        Environment environment = new Environment();
        environment.setDataSource(dataSource);
        return environment;
    }
    @Bean
    public Configuration configuration(){
        Environment environment = environment();
        Configuration configuration = new Configuration(environment);
        return  configuration;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(){
        Configuration configuration = configuration();
        return  new SqlSessionFactoryBuilder().builder(configuration,mapperPackageName);
    }
    @Bean
    public SqlSession sqlSession(){
        SqlSession sqlSession = sqlSessionFactory().openSession();

        return sqlSession;


    }

}