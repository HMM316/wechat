package org.liufeng.course.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.liufeng.course.util.MenuUtil;

@SuppressWarnings("serial")
public class Login extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code=request.getParameter("code");
		MenuUtil menuUtil=new MenuUtil();
		@SuppressWarnings("static-access")
		JSONObject jsonObject =menuUtil.doGetStr("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxe090dedac1387a6f&secret=ea6cbc3485f306ce8b97d3377d787a54&code="+code+"&grant_type=authorization_code");
		String access_token=(String) jsonObject.get("access_token");
		String openid=(String) jsonObject.get("openid");
		@SuppressWarnings("static-access")
		JSONObject user =menuUtil.doGetStr("https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang=zh_CN");
		System.out.println(user);
		response.sendRedirect("/");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	
}
