package org.liufeng.course.test;

import java.io.IOException;

import org.liufeng.course.util.MenuUtil;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import net.sf.json.JSONObject;

public class WeixinTest {
	public static void main(String[] args) throws ParseException, IOException {
		String menu = JSONObject.fromObject(MenuUtil.initMenu()).toString();
		int result=MenuUtil.createMenu("wshb0IUncI_erl425Jgeg5VqXxAECRu6llejegPyknPGUGpjfTDiEfPYqZTGdqi03PIrjRFYk-n4rzHs9iWADZ3OgWSp4ZMjQuc6ZmIXMXAERObACAKXU", menu);
		if(result==0){
			System.out.println("success");
		}else{
			System.out.println("error:"+result);
		}
	}
}
