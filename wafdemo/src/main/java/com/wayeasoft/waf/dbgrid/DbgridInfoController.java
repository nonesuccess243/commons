package com.wayeasoft.waf.dbgrid;

import com.wayeasoft.waf.springmvc.controller.BaseController;
import com.younker.waf.dbgrid.DBGrid;
import com.younker.waf.dbgrid.DBGridEngine;
import com.younker.waf.dbgrid.DBGrids;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

/**
 * Created by niyuzhe on 7/2/2017.
 */
@Controller
@RequestMapping("/internal/dbgrid")
public class DbgridInfoController extends BaseController
{
        @RequestMapping("/list.do")
        public String queryById(Model model)
        {

                Collection<DBGrid> dbgrids = DBGridEngine.getDefaultInstance().getDBGrids().getAllDBGrids();
                model.addAttribute("dbgrids", dbgrids);

                return "list";
        }
}
