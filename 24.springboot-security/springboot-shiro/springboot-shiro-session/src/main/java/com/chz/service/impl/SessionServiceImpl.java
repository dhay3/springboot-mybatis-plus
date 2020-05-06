package com.chz.service.impl;

import com.chz.entity.User;
import com.chz.entity.UserOnline;
import com.chz.service.ISessionService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("sessionService")
public class SessionServiceImpl implements ISessionService {
    @Autowired
    SessionDAO sessionDAO;

    @Override
    public List<UserOnline> list() {
        ArrayList<UserOnline> list = new ArrayList<>();
        //获取所有有效的session
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            UserOnline userOnline = new UserOnline();
            User user = new User();
            //用于追踪用户
            SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
            //根据登入用户的session key ,判断是否有用户
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                continue;
            } else {
                principalCollection = (SimplePrincipalCollection) session
                        .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                //获取用户
                user = (User) principalCollection.getPrimaryPrincipal();
                //设置信息
                userOnline.setId(user.getUId().toString());
                userOnline.setUsername(user.getName());
            }
            //获取session将要过期的时间,如果-1表示永不过期
            long timeout = session.getTimeout();
            if (0L == timeout) {
                userOnline.setStatus("离线");
            } else {
                userOnline.setStatus("在线");
            }
            userOnline.setTimeout(timeout);
            list.add(userOnline);
        }
        return list;
    }

    /**
     * 通过设置session的存活时间来强制退出
     */
    @Override
    public boolean forceLogout(String sessionId) {
        Session session = sessionDAO.readSession(sessionId);
        session.setTimeout(0);
        sessionDAO.delete(session);
        return true;
    }
}
