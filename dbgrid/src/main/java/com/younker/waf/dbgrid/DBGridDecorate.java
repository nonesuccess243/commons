package com.younker.waf.dbgrid;
import java.util.List;


/**
 *  装饰dbgrid数据的类。
 *  
 *  dbgrid从数据库中查询到数据后，如果配置了装饰器，则会对数据执行装饰器的handleData函数
 *  
 * @author niyuzhe
 *
 */
public interface DBGridDecorate
{
        public void handleData(List<DBGridRow> datas);

        public DBGridDecorate DEFAULT_INSTANCE = new DBGridDecorate()
        {

                @Override
                public void handleData(List<DBGridRow> datas)
                {
                        // TODO Auto-generated method stub
                        
                }};
}
