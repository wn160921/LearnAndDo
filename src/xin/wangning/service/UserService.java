package xin.wangning.service;

import xin.wangning.dao.UserdDao;
import xin.wangning.domain.User;

public class UserService {
    UserdDao userdDao = new UserdDao();
    public User findByName(String name){
        return userdDao.findByName(name);
    }

    public boolean checkLogin(String name,String password){
        System.out.println(name+password);
        User user = userdDao.findByName(name);
        if(user==null){
            System.out.println("null");
            return false;
        }else if(!user.getPassword().equals(password)){
            System.out.println("password");
            return false;
        }else if(user.getPassword().equals(password)){
            return true;
        }else {
            return false;
        }
    }
}
