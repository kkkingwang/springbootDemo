package com.example.demo.csd.controller;

import com.example.demo.csd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/loadUserInfo")
    @ResponseBody
    public Object loadUserInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        Map<String, Object> data = new HashMap<String, Object>();
        try{
            data = userService.loadUserInfo();
            data.put("success", true);
        }catch (Exception e){
            e.printStackTrace();
            data.put("success", false);
            data.put("content", "请求异常！");
        }
        return data;
    }
}
