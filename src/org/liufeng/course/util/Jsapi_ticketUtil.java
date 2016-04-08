package org.liufeng.course.util;

import java.io.IOException;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.liufeng.course.pojo.Js_AccessToken;
/**
 * Js_AccessToken工具类
 * @author Administrator
 *
 */
public class Jsapi_ticketUtil {
	private static final String JS_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	/**
	 * get请求
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doGetStr(String url) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		HttpResponse httpResponse = client.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		if(entity != null){
			String result = EntityUtils.toString(entity,"UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}
		return jsonObject;
	}
	
	/**
	 * POST请求
	 * @param url
	 * @param outStr
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doPostStr(String url,String outStr) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		httpost.setEntity(new StringEntity(outStr,"UTF-8"));
		HttpResponse response = client.execute(httpost);
		String result = EntityUtils.toString(response.getEntity(),"UTF-8");
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}
	/**
	 * 获取accessToken
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws SQLException 
	 */
	@SuppressWarnings("static-access")
	public static Js_AccessToken getAccessToken() throws ParseException, IOException, SQLException{
		Js_AccessToken js_token = new Js_AccessToken();
		MySQLUtil sql=new MySQLUtil();   
		String url = JS_ACCESS_TOKEN_URL.replace("ACCESS_TOKEN", sql.getAccessToken().getToken());
		JSONObject jsonObject = doGetStr(url);
		int result=jsonObject.getInt("errcode");
		if(result!=0){
			url = JS_ACCESS_TOKEN_URL.replace("ACCESS_TOKEN", new AccessTokenUtil().getAccessToken().getToken());
			jsonObject = doGetStr(url);
		}
		if(jsonObject!=null){
			js_token.setErrcode(jsonObject.getInt("errcode"));
			js_token.setErrmsg(jsonObject.getString("errmsg"));
			js_token.setExpires_in(Integer.parseInt(Long.toString(System.currentTimeMillis() / 1000)));
			js_token.setTicket(jsonObject.getString("ticket"));
		}
		sql.updateJS_AccessToken(jsonObject.getString("ticket"));
		return js_token;
	}
	
	public static void main(String[] args) throws ParseException, IOException, SQLException {
		getAccessToken();
	}
}
