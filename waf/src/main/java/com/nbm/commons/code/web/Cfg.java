package com.nbm.commons.code.web;


import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Cfg extends SimpleTagSupport{

	private String key;

	public String getKey(){
		return key;
	}

	public void setKey(String key){
		this.key = key;
	}

	@Override
	public void doTag() throws JspException, IOException{
		getJspContext().getOut().print(com.wayeasoft.core.configuration.Cfg.I.get(key, String.class));
	}
}