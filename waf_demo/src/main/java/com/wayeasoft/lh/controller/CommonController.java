package com.wayeasoft.lh.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nbm.core.modeldriven.PureModel;
import com.nbm.core.modeldriven.data.ModelRegister;
import com.wayeasoft.waf.springmvc.BaseController;

@RestController
public class CommonController extends BaseController{
	@Autowired
    public ModelRegister mr;
	 
	@RequestMapping(value = "/public/delete.do" , method =  RequestMethod.GET )
	public Map<String, Object> addSt(String id,String clz) {
		try {
			dao.deleteById(Class.forName("com.wayeasoft.lh.model." + clz).asSubclass(PureModel.class), Long.parseLong(id));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> modelMap =new HashMap<>();
		return modelMap;
	}
	
	@RequestMapping(value = "/public/getAllApi.do" , method =  RequestMethod.GET )
	public Map<String, Object> getAllApi() {
		Map<String, Object> modelMap =new HashMap<>();
		modelMap.put("mr", mr.getAllModel());
		return modelMap;
	}
}
