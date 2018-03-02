package com.nbm.commons.code.web;


import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Cfg2 extends SimpleTagSupport{

	private String keys;
	private String i;

	public String getKeys(){
		return keys;
	}

	public void setKeys(String keys){
		this.keys = keys;
	}

	public String getI(){
		return i;
	}

	public void setI(String i){
		this.i = i;
	}

	@Override
	public void doTag() throws JspException, IOException{
		String [] strings = com.wayeasoft.core.configuration.Cfg.I.get(keys, String [].class);
		if (strings != null) {
			for (String string : strings) {
				getJspContext().setAttribute(i, string);
				getJspBody().invoke(null);
			}
		}
	}
}