package com.fruitDayDB.service;

import com.fruitDayDB.dao.UserDao;
import com.fruitDayDB.dao.UserDaoImpl;
import com.fruitDayDB.vo.User;

import java.util.List;

/**
 * Created by soso.
 */
public class UserService {
    public static User add(User u){
        UserDao userDao=new UserDaoImpl();
        int num=userDao.add(u);
        if(num==1) {
            return UserService.login(u.getEmail(), u.getPwd(), true);
        }
        return null;
    }

    public static User login(String str,String pwd,boolean boo)
    {
        if (str == null || pwd == null) {
            return null;
        }
        UserDao userDao=new UserDaoImpl();
        User u=userDao.findByStr(str,boo);
        if (u == null || u.getPwd() == null) {
            return null;
        }

        if(pwd.equals(u.getPwd())) {
            u.setPwd("******");
            return u;
        }
        return null;
    }

    public static boolean del(User user)
    {
        UserDao userDao=new UserDaoImpl();
        int num=userDao.del(user);
        if(num==1)
            return true;
        else
            return false;
    }

    public static List<User> alluser()
    {
        UserDao userDao=new UserDaoImpl();

        return  userDao.findAll();
    }
    public static List<Integer> root()
    {
        UserDao userDao=new UserDaoImpl();

        return  userDao.root();
    }

    public static boolean upUser(User user)
    {
        UserDao userDao=new UserDaoImpl();
        int num=userDao.update(user);
        if(num==1)
            return true;
        else
            return false;
    }

    public static User findById(int id)
    {
        UserDao userDao=new UserDaoImpl();
        return  userDao.findById(id);
    }
}
