package com.example.demo.csd.service;

import com.example.demo.csd.dao.UserDao;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public Map<String, Object> loadUserInfo(){
        Map<String, Object> data = new HashMap<String, Object>();
        List<User> userList = userDao.getAll();
        data.put("data", userList);
        return data;
    }
}
