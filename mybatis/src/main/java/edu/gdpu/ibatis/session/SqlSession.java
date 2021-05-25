package edu.gdpu.ibatis.session;

import edu.gdpu.ibatis.exceptions.MapperException;
import edu.gdpu.ibatis.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 03月24日 14:32:58
 */
public interface SqlSession {
   <T> T getMapper(Class<T> mapperInterface) throws MapperException;

    Configuration getConfiguration();
   <T> T selectOne(String id,Object parameter);
    <E> List<E> selectList(String id);
    <E> List<E> selectList(String id, Object parameter);
    int insert(String id);

    int insert(String id,Object parameter);
    int update(String id);
    int update(String id,Object parameter);
    int delete(String id);
    int delete(String id,Object parameter);
    Connection getConnection() throws SQLException;
 public Executor getExecutor();
}