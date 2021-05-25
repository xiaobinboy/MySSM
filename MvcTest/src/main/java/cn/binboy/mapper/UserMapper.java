package cn.binboy.mapper;

import cn.binboy.domain.User;
import edu.gdpu.ibatis.annotation.Insert;
import edu.gdpu.ibatis.annotation.Select;
import edu.gdpu.spring.context.annotation.Repository;

/**
 * @author mazebin
 * @date 2021年 05月12日 09:51:43
 */
@Repository
public interface UserMapper {
    /**
     * 登录
     * @param user
     *
     * @return
     */
    @Select("select * from user where name=#{user.name} and age = #{user.age}")
    public User login(User user);

    /**
     * 注册
     * @param user
     */
    @Insert("insert into user values(#{user.name},#{user.age})")
    public void  register(User user);
}