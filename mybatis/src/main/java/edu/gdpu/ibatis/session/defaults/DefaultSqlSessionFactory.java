package edu.gdpu.ibatis.session.defaults;

import edu.gdpu.ibatis.executor.Executor;
import edu.gdpu.ibatis.session.Configuration;
import edu.gdpu.ibatis.session.SqlSession;
import edu.gdpu.ibatis.session.SqlSessionFactory;
import edu.gdpu.ibatis.spring.MapperScanRegistrar;

/**
 * @author mazebin
 * @date 2021年 03月24日 14:33:23
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;
    private MapperScanRegistrar mapperScanRegistrar;
    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public DefaultSqlSessionFactory(Configuration configuration, String packageName) {
        this.configuration = configuration;
        this.mapperScanRegistrar = new MapperScanRegistrar(packageName,configuration);
       this. InitMapperScan(this.mapperScanRegistrar);
    }

    /**
     * 初始化mapperProxy和MappedStatement的创建
     * @param mapperScanRegistrar
     */
    private  void InitMapperScan(MapperScanRegistrar mapperScanRegistrar){
        mapperScanRegistrar.scanMappedStatement();
        mapperScanRegistrar.scanMapper();
    }

    @Override
    public SqlSession openSession() {
        Executor executor = configuration.newExecutor();
         return new DefaultSqlSession(configuration,executor);

    }
}