package com.nbm.commons.code.web;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CfgIterTag extends SimpleTagSupport{

	private String key;
	private String var;

	public String getKey(){
		return key;
	}

	public void setKey(String key){
		this.key = key;
	}

	public String getVar(){
		return var;
	}

	public void setVar(String var){
		this.var = var;
	}

	@Override
	public void doTag() throws JspException, IOException{
		String [] strings = com.wayeasoft.core.configuration.Cfg.I.get(key, String [].class);
		if (strings != null) {
			for (String string : strings) {
				getJspContext().setAttribute(var, string);
				getJspBody().invoke(null);
			}
		}
	}
}