package edu.gdpu.mySSM.test.sqlSessionTest;

import com.alibaba.druid.pool.DruidDataSource;
import edu.gdpu.ibatis.exceptions.MapperException;
import edu.gdpu.ibatis.mapping.Environment;
import edu.gdpu.ibatis.session.Configuration;
import edu.gdpu.ibatis.session.SqlSession;
import edu.gdpu.ibatis.session.SqlSessionFactory;
import edu.gdpu.ibatis.session.SqlSessionFactoryBuilder;
import edu.gdpu.ibatis.spring.MapperScanRegistrar;
import edu.gdpu.mySSM.test.sqlSessionTest.dao.UserDao;

import edu.gdpu.mySSM.test.sqlSessionTest.domain.User;
import org.junit.Test;

import java.util.List;


/**
 * @author mazebin
 * @date 2021年 03月28日 17:45:54
 */
public class TestConfiguration {
    @Test
    public void testConfiguration() throws MapperException {
        //模拟扫描xml,获取configuration
       DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mySSMDemo?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("18050910ads");
        Environment environment = new Environment(dataSource);
        Configuration configuration = new Configuration(environment);
        String packageName ="edu.gdpu.mySSM.test.sqlSessionTest";
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().builder(configuration,packageName);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //System.out.println(sqlSession);
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        System.out.println(mapper.getClass());
//        List<User> all = mapper.findAll();
//        for (User user : all) {
//            System.out.println(user);
//
//        }

//         User byId = mapper.findByName("binboy");
//        System.out.println(byId);//User{name='binboy', age=11}
        User user = new User();
        user.setName("binboy");
        user.setAge(34);
        User binboy = mapper.find(user );
        System.out.println(binboy);
        // mapper.saveUser(new User("mzh",15));
      //  int i = mapper.deleteUser(new User("mzh", 20));
      //  System.out.println(i);

    }
}