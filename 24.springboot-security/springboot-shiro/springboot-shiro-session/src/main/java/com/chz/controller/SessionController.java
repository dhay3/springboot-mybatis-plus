package com.chz.controller;

import com.chz.entity.ResponseBo;
import com.chz.entity.UserOnline;
import com.chz.service.ISessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/online")
public class SessionController {
    @Autowired
    ISessionService sessionService;
    @RequestMapping("/index")
    public String online(){
        return "online";
    }
    @ResponseBody
    @RequestMapping("/list")
    public List<UserOnline> list(){
        return sessionService.list();
    }

    @ResponseBody
    @RequestMapping("/forceLogout")
    public ResponseBo forceLogout(String id){
        try {
            sessionService.forceLogout(id);
            return ResponseBo.error("踢出用户成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseBo.error("剔除用户失败");
    }
}

