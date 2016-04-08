package org.liufeng.course.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.liufeng.course.menu.Button;
import org.liufeng.course.menu.ClickButton;
import org.liufeng.course.menu.Menu;
import org.liufeng.course.menu.ViewButton;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class MenuUtil {
	//private static String CREATE_MENU_URL=" https://api.weixin.qq.com/cgi-bin/menu/create?access_token=wshb0IUncI_erl425Jgeg5VqXxAECRu6llejegPyknPGUGpjfTDiEfPYqZTGdqi03PIrjRFYk-n4rzHs9iWADZ3OgWSp4ZMjQuc6ZmIXMXAERObACAKXU";
	
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	/**
	 * ��ʼ���˵�  ��װ�˵�
	 * @return
	 */
	public static Menu initMenu(){
		Menu menu=new Menu();
		ViewButton button11=new ViewButton();
		button11.setUrl("http://m.sojn.com/product/tolicaishenqing.html");
		button11.setName("�������");
		button11.setType("view");
		
		ViewButton button12=new ViewButton();
		button12.setUrl("http://m.sojn.com/hotMoney/new.html");
		button12.setName("��Ʒ");
		button12.setType("view");
		
		ViewButton button13=new ViewButton();
		button13.setUrl("http://m.sojn.com/hotMoney/limit.html");
		button13.setName("��ʱ");
		button13.setType("view");
		
		ViewButton button14=new ViewButton();
		button14.setUrl("http://m.sojn.com/hotMoney/blue.html");
		button14.setName("����");
		button14.setType("view");
		
		ViewButton button15=new ViewButton();
		button15.setUrl("http://m.sojn.com/hotMoney/hot.html");
		button15.setName("����");
		button15.setType("view");
		
		ViewButton button21=new ViewButton();
		button21.setUrl("http://m.sojn.com/borrow/reply_borrow_first.html");
		button21.setName("������");
		button21.setType("view");
		
		ViewButton button22=new ViewButton();
		button22.setUrl("http://m.sojn.com/lendingPlatform/housingLoan.html");
		button22.setName("������");
		button22.setType("view");
		
		ViewButton button23=new ViewButton();
		button23.setUrl("http://m.sojn.com/lendingPlatform/consumerLoan.html");
		button23.setName("���Ѵ�");
		button23.setType("view");
		
		ViewButton button24=new ViewButton();
		button24.setUrl("http://m.sojn.com/lendingPlatform/autoLoan.html");
		button24.setName("������");
		button24.setType("view");
		
		ViewButton button25=new ViewButton();
		button25.setUrl("http://m.sojn.com/lendingPlatform/enterpriseLoan.html");
		button25.setName("��ҵ��");
		button25.setType("view");
		
		ViewButton button31=new ViewButton();
		button31.setUrl("http://m.sojn.com/user/registerPage.html");
		button31.setName("һ��ע��");
		button31.setType("view");
		
		ViewButton button32=new ViewButton();
		button32.setUrl("http://m.sojn.com/user/manager_register.html");
		button32.setName("������ס");
		button32.setType("view");
		
		ViewButton button33=new ViewButton();
		button33.setUrl("http://m.sojn.com/business/feedback.html");
		button33.setName("��Ϣ����");
		button33.setType("view");
		
		ClickButton button34=new ClickButton();
		button34.setName("�ѽ�");
		button34.setKey("34");
		button34.setType("click");
		
		ViewButton button35=new ViewButton();
		button35.setUrl("http://www.sojn.com/link/about.html");
		button35.setName("��������");
		button35.setType("view");
		
		/*ViewButton viewButton=new ViewButton();
		viewButton.setUrl("http://www.sojn.com");
		viewButton.setName("�ѽ���");
		viewButton.setType("view");
		
		ClickButton button3=new ClickButton();
		button3.setName("ɨ��");
		button3.setKey("33");
		button3.setType("scancode_push");
		
		ClickButton button4=new ClickButton();
		button4.setName("����λ��");
		button4.setKey("44");
		button4.setType("location_select");*/
		
		Button button1=new Button();
		button1.setName("��Ʋ�Ʒ");
		button1.setSub_button(new Button[]{button11,button12,button13,button14,button15});
		
		Button button2=new Button();
		button2.setName("�����Ʒ");
		button2.setSub_button(new Button[]{button21,button22,button23,button24,button25});
		
		Button button3=new Button();
		button3.setName("�ҵ�");
		button3.setSub_button(new Button[]{button31,button32,button33,button34,button35});
		
		Button[] buttons=new Button[]{button1,button2,button3};
		menu.setButton(buttons);
		return menu;
	}
	
	
	
	public static String createMenu(){
		  String menu2 = "{\"button\":[" +
		  		"{\"type\":\"click\",\"name\":\"�ѽ���\",\"key\":\"1\"}," +
		  		"{\"type\":\"click\",\"name\":\"���ÿ�\",\"key\":\"����\"}," +
		  		"{\"name\":\"�ճ�����\",\"sub_button\":[" +
		  			"{\"type\":\"click\",\"name\":\"���칤��\",\"key\":\"01_WAITING\"}," +
		  			"{\"type\":\"click\",\"name\":\"�Ѱ칤��\",\"key\":\"02_FINISH\"}," +
		  			"{\"type\":\"click\",\"name\":\"�ҵĹ���\",\"key\":\"03_MYJOB\"}," +
		  			"{\"type\":\"click\",\"name\":\"������Ϣ��\",\"key\":\"04_MESSAGEBOX\"}," +
		  			"{\"type\":\"click\",\"name\":\"ǩ��\",\"key\":\"05_SIGN\"}" +
		  			"]}" +
		  		"]}";
		  Menu menu=initMenu();
	        //�˴���Ϊ�Լ���Ҫ�Ľṹ�壬�滻����
	        try {
	           URL url = new URL(CREATE_MENU_URL);
	           HttpURLConnection http =   (HttpURLConnection) url.openConnection();    

	           http.setRequestMethod("POST");        
	           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
	           http.setDoOutput(true);        
	           http.setDoInput(true);
	           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//���ӳ�ʱ30��
	           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //��ȡ��ʱ30��
	           http.connect();
	           OutputStream os= http.getOutputStream();    
	           os.write(menu2.getBytes("UTF-8"));//�������    
	           os.flush();
	           os.close();

	           InputStream is =http.getInputStream();
	           int size =is.available();
	           byte[] jsonBytes =new byte[size];
	           is.read(jsonBytes);
	           String message=new String(jsonBytes,"UTF-8");
	           return "������Ϣ"+message;
	           } catch (MalformedURLException e) {
	               e.printStackTrace();
	           } catch (IOException e) {
	               e.printStackTrace();
	           }    
	        return "createMenu ʧ��";
	}
	
	
	/**
	 * get����
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
	 * POST����
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
	
	
	public static int createMenu(String token,String menu) throws ParseException, IOException{
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, menu);
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
}

