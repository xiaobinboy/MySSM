package cn.binboy.controller;

import cn.binboy.domain.User;
import cn.binboy.service.UserService;
import edu.gdpu.spring.context.annotation.AutoWrited;
import edu.gdpu.springmvc.bind.annotation.GetMapping;
import edu.gdpu.springmvc.bind.annotation.PostMapping;
import edu.gdpu.springmvc.bind.annotation.RequestParam;
import edu.gdpu.springmvc.bind.annotation.ResponseBody;
import edu.gdpu.springmvc.servlet.ModelAndView;
import edu.gdpu.spring.context.annotation.Controller;

/**
 * @author mazebin
 * @date 2021年 05月01日 21:25:31
 */
@Controller
public class HelloController {
    @AutoWrited
    private UserService userService;
    @GetMapping("/hello")
    @ResponseBody
    public  String hello(){
        return "hello  My SpringMvc";
    }
    @PostMapping("/getMV")
    public ModelAndView  mv(@RequestParam(name = "username") String username , @RequestParam(name = "age") Integer age){
        ModelAndView mv= new ModelAndView();
        User user = new User();

        user.setName(username);
        user.setAge(age);
        User login = userService.login(user);

        mv.setViewName("mv");
        mv.addObject("user",login);
        return  mv;
    }
    @GetMapping("/getString")
    public String renderString(){
        return  "getStringToMv";
    }
}