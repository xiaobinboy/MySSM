package cn.binboy.service.impl;

import cn.binboy.domain.User;
import cn.binboy.mapper.UserMapper;
import cn.binboy.service.UserService;
import edu.gdpu.spring.context.annotation.AutoWrited;
import edu.gdpu.spring.context.annotation.Service;

/**
 * @author mazebin
 * @date 2021年 05月12日 21:44:19
 */
@Service("userservice")
public class UserServiceImpl implements UserService {
    @AutoWrited
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        User login = userMapper.login(user);
        System.out.println(userMapper.getClass());
        System.out.println("从数据库查询："+login);
        return login;
    }

    @Override
    public void register(User user) {
userMapper.register(user);
    }
}