package cn.iocoder.springboot.lab68.lab69.service;

public class UserServiceImpl implements UserService {

    public void create(String username, String password) {
        System.out.println(String.format("登陆的用户名(%s) 密码(%s)", username, password));
    }

}
