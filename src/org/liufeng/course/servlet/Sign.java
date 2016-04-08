package org.liufeng.course.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.ParseException;
import org.liufeng.course.pojo.Js_AccessToken;
import org.liufeng.course.util.Jsapi_ticketUtil;
import org.liufeng.course.util.JssignUtil;
import org.liufeng.course.util.MySQLUtil;

@SuppressWarnings("serial")
public class Sign extends HttpServlet {

	@SuppressWarnings("static-access")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Js_AccessToken js_AccessToken=new Js_AccessToken();																//new一个Js_AccessToken对象
		MySQLUtil sql=new MySQLUtil();   																				//new一个MYSQLutil工具类
		Jsapi_ticketUtil jsapi_ticketUtil=new Jsapi_ticketUtil();														//new一个Jsapi_ticketUtil工具类
		js_AccessToken=sql.getJS_AccessToken();																			//先从数据库中获得jsapi——ticket
		System.out.println(js_AccessToken.getExpires_in());
		System.out.println(Integer.parseInt(Long.toString(System.currentTimeMillis() / 1000)));
		System.out.println(js_AccessToken.getExpires_in()-Integer.parseInt(Long.toString(System.currentTimeMillis() / 1000)));
		if(Integer.parseInt(Long.toString(System.currentTimeMillis() / 1000))-js_AccessToken.getExpires_in()>7000){		//判断是否超过7000秒
			try {
				js_AccessToken=jsapi_ticketUtil.getAccessToken();															//超过则通过Jsapi_ticketUtil工具类重新获取
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//获取完整的URL地址
		String reqUrl = request.getRequestURL().toString();
		String queryString = request.getQueryString(); 
		if (queryString != null) {
			reqUrl += "?"+queryString;
		}
		JssignUtil js=new JssignUtil();																					//获得jsapi签名
		Map<String,String> map=js.sign(js_AccessToken.getTicket(), reqUrl);
		for (Map.Entry entry : map.entrySet()) {
    		System.out.println(entry.getKey() + ", " + entry.getValue());
    	}
		request.getSession().setAttribute("map", map);
		request.getRequestDispatcher ("sign.jsp"). forward(request, response);											//放到map中转发
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
