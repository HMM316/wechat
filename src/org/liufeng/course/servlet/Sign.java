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
		Js_AccessToken js_AccessToken=new Js_AccessToken();																//newһ��Js_AccessToken����
		MySQLUtil sql=new MySQLUtil();   																				//newһ��MYSQLutil������
		Jsapi_ticketUtil jsapi_ticketUtil=new Jsapi_ticketUtil();														//newһ��Jsapi_ticketUtil������
		js_AccessToken=sql.getJS_AccessToken();																			//�ȴ����ݿ��л��jsapi����ticket
		System.out.println(js_AccessToken.getExpires_in());
		System.out.println(Integer.parseInt(Long.toString(System.currentTimeMillis() / 1000)));
		System.out.println(js_AccessToken.getExpires_in()-Integer.parseInt(Long.toString(System.currentTimeMillis() / 1000)));
		if(Integer.parseInt(Long.toString(System.currentTimeMillis() / 1000))-js_AccessToken.getExpires_in()>7000){		//�ж��Ƿ񳬹�7000��
			try {
				js_AccessToken=jsapi_ticketUtil.getAccessToken();															//������ͨ��Jsapi_ticketUtil���������»�ȡ
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//��ȡ������URL��ַ
		String reqUrl = request.getRequestURL().toString();
		String queryString = request.getQueryString(); 
		if (queryString != null) {
			reqUrl += "?"+queryString;
		}
		JssignUtil js=new JssignUtil();																					//���jsapiǩ��
		Map<String,String> map=js.sign(js_AccessToken.getTicket(), reqUrl);
		for (Map.Entry entry : map.entrySet()) {
    		System.out.println(entry.getKey() + ", " + entry.getValue());
    	}
		request.getSession().setAttribute("map", map);
		request.getRequestDispatcher ("sign.jsp"). forward(request, response);											//�ŵ�map��ת��
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
