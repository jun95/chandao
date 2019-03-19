package com.selfboot.chandao.util;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * Created by 87570 on 2019/3/18.
 */
public class SessionContext  {

    private HashMap<String,HttpSession> sessionMap;

    private SessionContext() {
        sessionMap = new HashMap<>();
    }

    public static SessionContext getInstance() {
        return SessionSingletonHolder.INSTANCE;
    }

    public synchronized void addSession(HttpSession session) {
        if (session != null) {
            sessionMap.put(session.getId(), session);
        }
    }

    public synchronized void delSession(HttpSession session) {
        if (session != null) {
            sessionMap.remove(session.getId());
        }
    }

    public synchronized HttpSession getSession(String sessionID) {
        if (sessionID == null) {
            return null;
        }
        return sessionMap.get(sessionID);
    }

    /** 写一个静态内部类，里面实例化外部类 */
    private static class SessionSingletonHolder {
        private static final SessionContext INSTANCE = new SessionContext();
    }
}
