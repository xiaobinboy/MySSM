package edu.gdpu.mySSM.test.sqlSessionTest.dao;

import edu.gdpu.ibatis.annotation.Delete;
import edu.gdpu.ibatis.annotation.Insert;
import edu.gdpu.ibatis.annotation.Select;
import edu.gdpu.ibatis.annotation.Update;
import edu.gdpu.mySSM.test.sqlSessionTest.domain.User;


import java.util.List;


public interface UserDao {
    @Select("select * from user")
    public List<User> findAll();
    @Select("select  * from user where name = #{username}")
    public User findByName(String username);
    @Insert("insert into user values(#{user.name},#{user.age})")
    public int saveUser(User user);
    @Update("update user set age = #{age} where name =#{name} ")
    public int updateUser(User user);
    @Delete("delete from user where name=#{name}")
    public int deleteUser(User user);
    @Select("select * from user where name=#{user.name} and age = #{user.age}"
    )
    public  User find(User user);


}
