/***********************************************
 * Title:       TimerTimer.java
 * Description: TimerTimer.java
 * Create Date: 2013-9-7
 * CopyRight:   CopyRight(c)@2013
 * Company:     NBM
 ***********************************************
 */
package com.nbm.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class TimerTimer
{
        private final static Logger log = LoggerFactory.getLogger(TimerTimer.class);
        private long start;
        private long last;
        
        public TimerTimer()
        {
                last = start = System.currentTimeMillis();
        }
        
        public void peek()
        {
                long now = System.currentTimeMillis();
                log.debug("耗时{}毫秒，从开始计时至此共计{}毫秒", now-last, now-start);
                last = now;
        }
        
        public final static void main(String [] args ) throws InterruptedException
        {
                TimerTimer timer = new TimerTimer();
                timer.peek();
                Thread.sleep(1000);
                timer.peek();
                Thread.sleep(1000);
                timer.peek();
              
                
                
        }

}
