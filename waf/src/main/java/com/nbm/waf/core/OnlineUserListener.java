package com.nbm.waf.core;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class OnlineUserListener
 *
 */
//@WebListener
public class OnlineUserListener implements HttpSessionListener
{
        public String ONLINE_INFO_APPLICATION_KEY = "online_info_application";

        /**
         * Default constructor.
         */
        public OnlineUserListener()
        {
                
        }

        /**
         * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
         */
        public void sessionCreated(HttpSessionEvent event)
        {
                getSessionMap(event).put(event.getSession().getId(), event.getSession());
        }

        /**
         * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
         */
        public void sessionDestroyed(HttpSessionEvent event)
        {
                getSessionMap(event).put(event.getSession().getId(), event.getSession());
        }
        
        public Map<String, HttpSession> getSessionMap(HttpSessionEvent event)
        {
                Map<String, HttpSession> sessionMap = (Map<String, HttpSession>) event.getSession().getServletContext().getAttribute(ONLINE_INFO_APPLICATION_KEY);
                if( sessionMap == null )
                {
                        sessionMap = new HashMap<>();
                        event.getSession().getServletContext().setAttribute(ONLINE_INFO_APPLICATION_KEY, sessionMap);
                        
                }
                
                return sessionMap;
        }

}
