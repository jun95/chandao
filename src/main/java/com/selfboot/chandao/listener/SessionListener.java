package com.selfboot.chandao.listener;

import com.selfboot.chandao.util.SessionContext;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by 87570 on 2019/3/18.
 * session监听器
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    private SessionContext sessionContext = SessionContext.getInstance();

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        sessionContext.addSession(httpSessionEvent.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        sessionContext.delSession(httpSessionEvent.getSession());
    }
}
