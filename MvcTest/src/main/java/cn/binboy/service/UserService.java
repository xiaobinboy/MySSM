package cn.binboy.service;


import cn.binboy.domain.User;

/**
 * @author mazebin
 * @date 2021年 05月01日 21:25:41
 */

public interface UserService {

    public User login(User user);
    public void  register(User user);
}