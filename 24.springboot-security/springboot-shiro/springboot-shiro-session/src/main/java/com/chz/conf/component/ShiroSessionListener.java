package com.chz.conf.component;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * session查看当前系统的在线人数，查看这些在线用户的一些基本信息，强制让某个用户下线等。
 */
public class ShiroSessionListener implements SessionListener {
    //Atomic保证了数据操作的原子性, 用于统计在线数
    private final AtomicInteger sessionCount = new AtomicInteger(0);

    @Override
    public void onStart(Session session) {
        sessionCount.getAndIncrement();
    }

    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
    }

    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
    }
}
