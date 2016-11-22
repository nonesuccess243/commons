/******************************************************************************
 * Title:     Younker Web Appliaction Framework
 * Copyright: Copyright (c) 2004
 * Company:   YounkerSoft
 * Author: 	  Xiao Jian
 * Version:   2.0
 *****************************************************************************/
package com.younker.waf.dbgrid;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * GBGridRow.java
 */
public class DBGridRow implements Serializable
{
        /**
         * 
         */
        private static final long serialVersionUID = -4080833451261432169L;

        private String id;

        private ArrayList<DBGridItem> items;

        public DBGridRow()
        {
        }

        public DBGridRow(String id)
        {
                this.id = id;
        }

        public DBGridRow(String id, int column)
        {
                this.id = id;
                items = new ArrayList<DBGridItem>(column);
        }

        public void addItem(DBGridItem item)
        {
                items.add(item);
        }

        public String getId()
        {
                return id;
        }

        public void setId(String s)
        {
                id = s;
        }

        public ArrayList<DBGridItem> getItems()
        {
                return items;
        }

        @Override
        public String toString()
        {
                return "DBGridRow [id=" + id + ", items=" + items + "]";
        }
}
