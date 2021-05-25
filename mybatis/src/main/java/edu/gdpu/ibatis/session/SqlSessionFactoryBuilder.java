package edu.gdpu.ibatis.session;

import edu.gdpu.ibatis.session.defaults.DefaultSqlSessionFactory;

/**
 * @author mazebin
 * @date 2021年 03月24日 14:34:36
 */
public class SqlSessionFactoryBuilder  {
    public SqlSessionFactory builder(Configuration configuration){
        return new DefaultSqlSessionFactory(configuration);
    }
    public SqlSessionFactory builder(Configuration configuration ,String packageName){
        return  new DefaultSqlSessionFactory(configuration,packageName);
    }
}