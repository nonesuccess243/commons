package com.wayeasoft.waf.dbgrid.web;

import com.younker.waf.dbgrid.DBGrid;
import com.younker.waf.dbgrid.DBGridEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

/**
 * Created by niyuzhe on 7/2/2017.
 */
@Controller
@RequestMapping("/internal/dbgrid")
public class DbgridInfoController 
{
        @RequestMapping("/list.do")
        public String queryById(Model model)
        {

                Collection<DBGrid> dbgrids = DBGridEngine.getDefaultInstance().getDBGrids().getAllDBGrids();
                model.addAttribute("dbgrids", dbgrids);

                return "/dbgrid/list";
        }
}
