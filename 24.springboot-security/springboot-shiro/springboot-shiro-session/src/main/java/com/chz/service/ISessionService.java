package com.chz.service;

import com.chz.entity.UserOnline;

import java.util.List;

public interface ISessionService {
    //查看所有在线人数
    List<UserOnline> list();
    //强制踢出用户
    boolean forceLogout(String sessionId);
}
