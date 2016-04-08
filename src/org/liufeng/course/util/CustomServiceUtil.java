package org.liufeng.course.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * �ͷ��ӿ� ʵ�ָ��û�����Ϣ
 * @author Administrator
 *
 */
public class CustomServiceUtil {
	  /**

     * ��������΢�ź�̨�ӿ�

     * @param action �ӿ�url

     * @param json  ����ӿڴ��͵�json�ַ���

     */

    public int connectWeiXinInterface(String action,String json){
    	int result;
        URL url;

       try {

           url = new URL(action);

           HttpURLConnection http = (HttpURLConnection) url.openConnection();

           http.setRequestMethod("POST");

           http.setRequestProperty("Content-Type",

                   "application/x-www-form-urlencoded");

           http.setDoOutput(true);

           http.setDoInput(true);

           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// ���ӳ�ʱ30��

           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // ��ȡ��ʱ30��

           http.connect();

           OutputStream os = http.getOutputStream();

           os.write(json.getBytes("UTF-8"));// �������

           InputStream is = http.getInputStream();

           int size = is.available();

           byte[] jsonBytes = new byte[size];

           is.read(jsonBytes);
           
           String resultstr = new String(jsonBytes, "UTF-8");

           result=Integer.parseInt(resultstr.split(",")[0].split(":")[1]);
           
           System.out.println("���󷵻ؽ��:"+resultstr.split(",")[0].split(":")[1]);
           
           os.flush();

           os.close();
           return result;

       } catch (Exception e) {

           e.printStackTrace();
           
           return 0;
       }
    }
    
    
    /**

     * ΢�Ź����˺ŷ��͸��˺�

     * @param content �ı�����

     * @param toUser ΢���û�  

     * @return

     */

    @SuppressWarnings("static-access")
	public  void sendTextMessageToUser(String content,String toUser){

       String json = "{\"touser\": \""+toUser+"\",\"msgtype\": \"text\", \"text\": {\"content\": \""+content+"\"}}";

       //��ȡ����·��

       String action = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+new MySQLUtil().getAccessToken().getToken();

       try {

          int result = connectWeiXinInterface(action,json);
          if(result==0){
  			System.out.println("success");
  		}else{
  			System.out.println("error:"+result);
  			if(result!=0){
  				AccessTokenUtil accessToken=new AccessTokenUtil();
  				action = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accessToken.getAccessToken().getToken();
  				connectWeiXinInterface(action,json);
  			}
  		}

       } catch (Exception e) {

           e.printStackTrace();

       }

   }
}
