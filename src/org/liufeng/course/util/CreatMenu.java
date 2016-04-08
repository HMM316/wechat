package org.liufeng.course.util;

import java.io.IOException;
import java.sql.SQLException;

import net.sf.json.JSONObject;


import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;


public class CreatMenu {
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws ParseException, IOException, org.apache.http.ParseException, SQLException {
		//MenuUtil me=new MenuUtil();
		//me.createMenu();
		
		String menu = JSONObject.fromObject(MenuUtil.initMenu()).toString();
		MySQLUtil sql=new MySQLUtil();
		int result=MenuUtil.createMenu(sql.getAccessToken().getToken(), menu);
		if(result==0){
			System.out.println("success");
		}else{
			System.out.println("error:"+result);
 			if(result!=0){
				AccessTokenUtil accessToken=new AccessTokenUtil();
				MenuUtil.createMenu(accessToken.getAccessToken().getToken(), menu);
			}
		}
	}
}