package com.younker.waf.db.mybatis;

import java.util.Date;

import org.joda.time.DateTime;

import com.younker.waf.utils.StringUtil;

public class SqlSessionInfo
{
        private Date startTime;
        private Date endTime;
        private Thread thread;
        
        /**
         * 表示该sqlsession所需的其它信息，由调用端控制
         */
        private String info;
        
        private String uri;

        public Date getStartTime()
        {
                return startTime;
        }

        public void setStartTime(Date startTime)
        {
                this.startTime = startTime;
        }

        public Date getEndTime()
        {
                return endTime;
        }

        public void setEndTime(Date endTime)
        {
                this.endTime = endTime;
        }

        public Thread getThread()
        {
                return thread;
        }

        public void setThread(Thread thread)
        {
                this.thread = thread;
        }

        public String getInfo()
        {
                return info;
        }

        public void setInfo(String info)
        {
                this.info = info;
        }

        public String getUri()
        {
                return uri;
        }

        public void setUri(String uri)
        {
                this.uri = uri;
        }

        @Override
        public String toString()
        {
                
                long lastTime = 0;
                if( endTime == null )
                {
                        lastTime = new Date().getTime() - startTime.getTime();
                }else
                {
                        lastTime = endTime.getTime() - startTime.getTime();
                }
                return StringUtil.getString("uri=[{}], [{}] - [{}], 持续[{}ms]", uri, new DateTime(startTime).toString(),new DateTime(endTime).toString(), lastTime);
        }
        
}
