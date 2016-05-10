package com.younker.waf.dbgrid;
import java.util.List;

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
