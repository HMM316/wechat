package org.liufeng.course.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONOUtil.JsonUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.liufeng.course.message.resp.Article;
import org.liufeng.course.message.resp.NewsMessage;
import org.liufeng.course.message.resp.TextMessage;
import org.liufeng.course.util.CustomServiceUtil;
import org.liufeng.course.util.MessageUtil;

/**
 * ���ķ�����
 * 
 * @author liufeng
 * @date 2013-12-02
 */
public class CoreService {
	
	private static final String SOJN_URL = "http://www.sojn.com";
	/**
	 * ����΢�ŷ���������
	 * 
	 * @param request
	 * @return xml
	 */
	@SuppressWarnings("static-access")
	public static String processRequest(HttpServletRequest request) {
		// xml��ʽ����Ϣ����
		String respXml = null;
		// Ĭ�Ϸ��ص��ı���Ϣ����
		String respContent = "��л��ʹ���ѽ���΢�ŷ����������Ӧ����ţ��鿴�����Ϣ:\n" +
							"       1�����²ƾ���Ѷ\n" +
							"       2����Ʋ�Ʒ�Ƽ�\n" +
							"       3�������Ʒ�Ƽ�\n" +
							"       4���ƾ�����\n" +
							"       5��ׯ�����\n" +
							"       6����  ��  ��\n" +
							"       7��ʯ�Ž���\n" +
							"       8��ׯ���\n" +
							"����Ҳ����������Ҳ��ʽ������~\n" +
							"���͡������鿴���˵�";
		try {
			// ����parseXml��������������Ϣ
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// ���ͷ��ʺ�
			String fromUserName = requestMap.get("FromUserName");
			// ������΢�ź�
			String toUserName = requestMap.get("ToUserName");
			// ��Ϣ����
			String msgType = requestMap.get("MsgType");
			// ��Ϣ����ʱ��
			String createTime = requestMap.get("CreateTime");
			
			// �ı���Ϣ
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				String content = requestMap.get("Content");
				if(!"?".equals(content)&&!"��".equals(content)&&!"1".equals(content)&&!"2".equals(content)&&!"3".equals(content)&&!"4".equals(content)&&!"5".equals(content)&&!"6".equals(content)&&!"7".equals(content)&&!"8".equals(content)&&!"s".equals(content)){
					respContent = ChatService.chat(fromUserName, createTime, content);
				}
				if("1".equals(content)){
			        // �ظ�ͼ����Ϣ
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(8);
					List<Article> articles =new ArrayList<Article>();
					Document doc = Jsoup.connect("http://www.sojn.com/cjzx/listlatestNews.html").get();//�����ѽ�����ȡ��������
			        Elements li_data = doc.getElementsByTag("li");										//����html װ������
			        for(int i=0; i<li_data.size() ; i++){
			        	Article article=new Article();
			        	Elements A_datas = li_data.get(i).getElementsByTag("a");
			        	Elements img_data = A_datas.get(0).getElementsByTag("img");
						article.setUrl("http://m.sojn.com/frontNews/tonewsInfo.html?id="+A_datas.get(0).attr("href").substring(24));
						article.setTitle(A_datas.get(1).text());
						article.setPicUrl(SOJN_URL+img_data.get(0).attr("src"));
						articles.add(article);
						if(articles.size()==8){
							break;
						}
			        }
					newsMessage.setArticles(articles);
					// ���ı���Ϣ����ת����xml
					respXml = MessageUtil.messageToXml(newsMessage);
				}else if("2".equals(content)){
					  // �ظ�ͼ����Ϣ
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(8);
					List<Article> articles =new ArrayList<Article>();
					Document doc = Jsoup.connect("http://www.sojn.com/lccp/lccprank.html?i=1").get();//�����ѽ�����ȡ��������
			        Elements li_data = doc.getElementsByTag("li");										//����html װ������
			        for(int i=0; i<li_data.size() ; i++){
			        	Elements A_datas = li_data.get(i).getElementsByTag("a");
			        	Elements img_data = li_data.get(i).getElementsByTag("input");
			        	if(!A_datas.get(0).attr("href").equals("javascript:void(0)")){
			        		Article article=new Article();
			        		article.setUrl("http://m.sojn.com/hotMoney/findbycp.html?productID="+A_datas.get(0).attr("href").substring(30));
			        		article.setTitle(A_datas.get(0).text());
			        		article.setPicUrl(SOJN_URL+img_data.get(0).attr("value"));
			        		articles.add(article);
			        	}
						if(articles.size()==8){
							break;
						}
			        }
					newsMessage.setArticles(articles);
					// ���ı���Ϣ����ת����xml
					respXml = MessageUtil.messageToXml(newsMessage);
				}else if("3".equals(content)){
					// �ظ�ͼ����Ϣ
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(7);
					List<Article> articles =new ArrayList<Article>();
					JsonUtil json=new JsonUtil();
					JSONArray arr= json.getJson("http://www.sojn.com/lccp/isrecommended.html");//�����ѽ�����ȡ�Ƽ������Ʒ
					//����json װ������
			        for(int i=0; i<arr.size() ; i++){
			        	Article article=new Article();
						JSONObject jsonObject=JSONObject.fromObject(arr.get(i).toString());
						String productType=jsonObject.get("productType").toString();
						String productID=jsonObject.get("link").toString().substring(28);
						if(productType.equals("215")){
							article.setUrl("http://m.sojn.com/lendingPlatform/housingDetail.html?productID="+productID);
						}else if(productType.equals("216")){
							article.setUrl("http://m.sojn.com/lendingPlatform/autoDetail.html?productID="+productID);
						}else if(productType.equals("217")){
							article.setUrl("http://m.sojn.com/lendingPlatform/enterpriseDetail.html?productID="+productID);
						}else{
							article.setUrl("http://m.sojn.com/lendingPlatform/consumerDetail.html?productID="+productID);
						};
						article.setTitle(jsonObject.get("productName").toString());
						article.setPicUrl(SOJN_URL+jsonObject.get("productDetailFigure").toString());
						articles.add(article);
						if(articles.size()==7){
							break;
						}
			        }
					newsMessage.setArticles(articles);
					// ���ı���Ϣ����ת����xml
					respXml = MessageUtil.messageToXml(newsMessage);
				}else if("4".equals(content)){
					// �ظ�ͼ����Ϣ
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(1);
					List<Article> articles =new ArrayList<Article>();
					JsonUtil json=new JsonUtil();
					JSONObject jsonObject= json.getJsonPost("http://www.sojn.com/cjzx/indexNews.html?location=N33");//�����ѽ�����ȡ�Ƽ������Ʒ
					//����json װ������
			        Article article=new Article();
			        JSONObject jsonObject2=JSONObject.fromObject(jsonObject.get("newsPic"));
					article.setUrl("http://m.sojn.com/frontNews/tonewsInfo.html?id="+jsonObject2.get("newID"));
					article.setTitle(jsonObject2.get("newTitle").toString());
					article.setPicUrl(SOJN_URL+jsonObject.get("pic").toString());
					article.setDescription(jsonObject2.get("newSummary").toString());
					articles.add(article);
					newsMessage.setArticles(articles);
					// ���ı���Ϣ����ת����xml
					respXml = MessageUtil.messageToXml(newsMessage);
				}else if("5".equals(content)){
					// �ظ�ͼ����Ϣ
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(1);
					List<Article> articles =new ArrayList<Article>();
					JsonUtil json=new JsonUtil();
					JSONObject jsonObject= json.getJsonPost("http://www.sojn.com/cjzx/indexNews.html?location=N35");//�����ѽ�����ȡ�Ƽ������Ʒ
					//����json װ������
			        Article article=new Article();
			        JSONObject jsonObject2=JSONObject.fromObject(jsonObject.get("newsPic"));
					article.setUrl("http://m.sojn.com/frontNews/tonewsInfo.html?id="+jsonObject2.get("newID"));
					article.setTitle(jsonObject2.get("newTitle").toString());
					article.setPicUrl(SOJN_URL+jsonObject.get("pic").toString());
					article.setDescription(jsonObject2.get("newSummary").toString());
					articles.add(article);
					newsMessage.setArticles(articles);
					// ���ı���Ϣ����ת����xml
					respXml = MessageUtil.messageToXml(newsMessage);
				}else if("6".equals(content)){
					// �ظ�ͼ����Ϣ
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(1);
					List<Article> articles =new ArrayList<Article>();
					JsonUtil json=new JsonUtil();
					JSONObject jsonObject= json.getJsonPost("http://www.sojn.com/cjzx/indexNews.html?location=N37");//�����ѽ�����ȡ�Ƽ������Ʒ
					//����json װ������
			        Article article=new Article();
			        JSONObject jsonObject2=JSONObject.fromObject(jsonObject.get("newsPic"));
					article.setUrl("http://m.sojn.com/frontNews/tonewsInfo.html?id="+jsonObject2.get("newID"));
					article.setTitle(jsonObject2.get("newTitle").toString());
					article.setPicUrl(SOJN_URL+jsonObject.get("pic").toString());
					article.setDescription(jsonObject2.get("newSummary").toString());
					articles.add(article);
					newsMessage.setArticles(articles);
					// ���ı���Ϣ����ת����xml
					respXml = MessageUtil.messageToXml(newsMessage);
				}else if("7".equals(content)){
					// �ظ�ͼ����Ϣ
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(2);
					List<Article> articles =new ArrayList<Article>();
					JsonUtil json=new JsonUtil();
					JSONObject jsonObject= json.getJsonPost("http://www.sojn.com/cjzx/indexNews.html?location=N38-1");//�����ѽ�����ȡ�Ƽ������Ʒ
					//����json װ������
			        Article article=new Article();
			        JSONObject jsonObject2=JSONObject.fromObject(jsonObject.get("newsPic"));
					article.setUrl("http://m.sojn.com/frontNews/tonewsInfo.html?id="+jsonObject2.get("newID"));
					article.setTitle(jsonObject2.get("newTitle").toString());
					article.setPicUrl(SOJN_URL+jsonObject.get("pic").toString());
					article.setDescription(jsonObject2.get("newSummary").toString());
					articles.add(article);
					
					JSONObject jsonObjectN38_2= json.getJsonPost("http://www.sojn.com/cjzx/indexNews.html?location=N38-2");//�����ѽ�����ȡ�Ƽ������Ʒ
					//����json װ������
					Article article2=new Article();
					JSONObject jsonObjectN38_2_2=JSONObject.fromObject(jsonObjectN38_2.get("newsPic"));
					article2.setUrl("http://m.sojn.com/frontNews/tonewsInfo.html?id="+jsonObjectN38_2_2.get("newID"));
					article2.setTitle(jsonObjectN38_2_2.get("newTitle").toString());
					article2.setPicUrl(SOJN_URL+jsonObjectN38_2.get("pic").toString());
					article2.setDescription(jsonObjectN38_2_2.get("newSummary").toString());
					articles.add(article2);
					newsMessage.setArticles(articles);
					// ���ı���Ϣ����ת����xml
					respXml = MessageUtil.messageToXml(newsMessage);
				}else if("8".equals(content)){
					// �ظ�ͼ����Ϣ
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(2);
					List<Article> articles =new ArrayList<Article>();
					JsonUtil json=new JsonUtil();
					JSONObject jsonObject= json.getJsonPost("http://www.sojn.com/cjzx/indexNews.html?location=N39-1");//�����ѽ�����ȡ�Ƽ������Ʒ
					//����json װ������
			        Article article=new Article();
			        JSONObject jsonObject2=JSONObject.fromObject(jsonObject.get("newsPic"));
					article.setUrl("http://m.sojn.com/frontNews/tonewsInfo.html?id="+jsonObject2.get("newID"));
					article.setTitle(jsonObject2.get("newTitle").toString());
					article.setPicUrl(SOJN_URL+jsonObject.get("pic").toString());
					article.setDescription(jsonObject2.get("newSummary").toString());
					articles.add(article);
					
					JSONObject jsonObjectN39_2= json.getJsonPost("http://www.sojn.com/cjzx/indexNews.html?location=N39-2");//�����ѽ�����ȡ�Ƽ������Ʒ
					//����json װ������
					Article article2=new Article();
					JSONObject jsonObjectN39_2_2=JSONObject.fromObject(jsonObjectN39_2.get("newsPic"));
					article2.setUrl("http://m.sojn.com/frontNews/tonewsInfo.html?id="+jsonObjectN39_2_2.get("newID"));
					article2.setTitle(jsonObjectN39_2_2.get("newTitle").toString());
					article2.setPicUrl(SOJN_URL+jsonObjectN39_2.get("pic").toString());
					article2.setDescription(jsonObjectN39_2_2.get("newSummary").toString());
					articles.add(article2);
					newsMessage.setArticles(articles);
					// ���ı���Ϣ����ת����xml
					respXml = MessageUtil.messageToXml(newsMessage);
				}else if("s".equals(content)){
					TextMessage textMessage = new TextMessage();
					textMessage.setToUserName(fromUserName);
					textMessage.setFromUserName(toUserName);
					textMessage.setCreateTime(new Date().getTime());
					textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					textMessage.setContent("�ù������ڿ����У���л����֧������⡣\n�������<a href='http://m.sojn.com/weixin/sign.html'>�ѽ���</a>");
					//textMessage.setContent("�ù������ڿ����У���л����֧������⡣\n�������<a href='http://m.sojn.com/Mydemo'>�ѽ���</a>");
					// ���ı���Ϣ����ת����xml
					respXml = MessageUtil.messageToXml(textMessage);
				}else{
					// �ظ��ı���Ϣ
					TextMessage textMessage = new TextMessage();
					textMessage.setToUserName(fromUserName);
					textMessage.setFromUserName(toUserName);
					textMessage.setCreateTime(new Date().getTime());
					textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					textMessage.setContent(respContent);
					// ���ı���Ϣ����ת����xml
					respXml = MessageUtil.messageToXml(textMessage);
				}
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
				String Recognition = requestMap.get("Recognition");
				if(!"".equals(Recognition)){
					/**
					 * ͨ��ʶ����������ݽ��лظ�
					 */
					respContent = ChatService.chat(fromUserName, createTime, Recognition);
				}
				/**
				 * ���ÿͷ��ӿڽ��лظ���Ϣ
				 */
				CustomServiceUtil cc=new CustomServiceUtil();
				//cc.sendTextMessageToUser("�����͵�������Ϣ�ǣ�"+Recognition, fromUserName);
				cc.sendTextMessageToUser(respContent, fromUserName);
			}else if(msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_IMAGE)){
				// �ظ�ͼ����Ϣ
				NewsMessage newsMessage = new NewsMessage();
				newsMessage.setToUserName(fromUserName);
				newsMessage.setFromUserName(toUserName);
				newsMessage.setCreateTime(new Date().getTime());
				newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setArticleCount(3);
				List<Article> articles =new ArrayList<Article>();
				Article article=new Article();
				article.setDescription("������������Ϣ��");
				article.setUrl("http://www.baidu.com");
				article.setTitle("�����Ǳ���");
				article.setPicUrl("http://ming.ngrok.cc/Mydemo/image/sd.jpg");
				
				Article article2=new Article();
				article2.setDescription("������������Ϣ��");
				article2.setUrl("http://www.baidu.com");
				article2.setTitle("�����Ǳ���");
				article2.setPicUrl("http://ming.ngrok.cc/Mydemo/image/sd.jpg");
				
				Article article3=new Article();
				article3.setDescription("������������Ϣ��");
				article3.setUrl("http://www.baidu.com");
				article3.setTitle("�����Ǳ���");
				article3.setPicUrl("http://ming.ngrok.cc/Mydemo/image/sd.jpg");
				
				articles.add(article);
				articles.add(article2);
				articles.add(article3);
				newsMessage.setArticles(articles);
				// ���ı���Ϣ����ת����xml
				respXml = MessageUtil.messageToXml(newsMessage);
			}else if((msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT))){
				String eventType = requestMap.get("Event");

				if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){			//��ͨ����¼�
					String key = requestMap.get("EventKey");
					if("11".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("�ù������ڿ����У���л����֧������⡣�������Ⱥ�С���Ļ��죬֧��������~");
						// ���ı���Ϣ����ת����xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("12".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("�ù������ڿ����У���л����֧������⡣�������Ⱥ�С���Ļ��죬֧��������~");
						// ���ı���Ϣ����ת����xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("13".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("�ù������ڿ����У���л����֧������⡣�������Ⱥ�С���Ļ��죬֧��������~");
						// ���ı���Ϣ����ת����xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("14".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("�ù������ڿ����У���л����֧������⡣�������Ⱥ�С���Ļ��죬֧��������~");
						// ���ı���Ϣ����ת����xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("15".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("�ù������ڿ����У���л����֧������⡣�������Ⱥ�С���Ļ��죬֧��������~");
						// ���ı���Ϣ����ת����xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("21".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("�ù������ڿ����У���л����֧������⡣�������Ⱥ�С���Ļ��죬֧��������~");
						// ���ı���Ϣ����ת����xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("22".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("�ù������ڿ����У���л����֧������⡣�������Ⱥ�С���Ļ��죬֧��������~");
						// ���ı���Ϣ����ת����xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("23".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("�ù������ڿ����У���л����֧������⡣�������Ⱥ�С���Ļ��죬֧��������~");
						// ���ı���Ϣ����ת����xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("24".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("�ù������ڿ����У���л����֧������⡣�������Ⱥ�С���Ļ��죬֧��������~");
						// ���ı���Ϣ����ת����xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("25".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("�ù������ڿ����У���л����֧������⡣�������Ⱥ�С���Ļ��죬֧��������~");
						// ���ı���Ϣ����ת����xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("31".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("�ù������ڿ����У���л����֧������⡣\n�������<a href='http://www.sojn.com'>�ѽ���</a>");
						// ���ı���Ϣ����ת����xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("32".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("�ù������ڿ����У���л����֧������⡣\n�������<a href='http://www.sojn.com'>�ѽ���</a>");
						// ���ı���Ϣ����ת����xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("33".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("�ù������ڿ����У���л����֧������⡣\n�������<a href='http://www.sojn.com'>�ѽ���</a>");
						// ���ı���Ϣ����ת����xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("34".equals(key)){///
						  // �ظ�ͼ����Ϣ
						NewsMessage newsMessage = new NewsMessage();
						newsMessage.setToUserName(fromUserName);
						newsMessage.setFromUserName(toUserName);
						newsMessage.setCreateTime(new Date().getTime());
						newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						newsMessage.setArticleCount(3);
						List<Article> articles =new ArrayList<Article>();
						Document doc = Jsoup.connect("http://www.sojn.com/forum/newhdsj.html?BBSType=278&limit=3").get();//�����ѽ�����ȡ��������
				        Elements li_data = doc.getElementsByTag("li");										//����html װ������
				        for(int i=0; i<li_data.size() ; i++){
				        	Article article=new Article();
				        	Elements A_datas = li_data.get(i).getElementsByTag("a");
				        	Elements span_datas = li_data.get(i).getElementsByTag("span");
				        	Elements img_data = A_datas.get(0).getElementsByTag("img");
							article.setUrl(SOJN_URL+A_datas.get(0).attr("href"));
							article.setTitle(span_datas.get(0).text());
							article.setPicUrl(SOJN_URL+img_data.get(0).attr("src"));
							articles.add(article);
							if(articles.size()==3){
								break;
							}
				        }
						newsMessage.setArticles(articles);
						// ���ı���Ϣ����ת����xml
						respXml = MessageUtil.messageToXml(newsMessage);
					}else if("35".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("�ù������ڿ����У���л����֧������⡣\n�������<a href='http://www.sojn.com'>�ѽ���</a>");
						// ���ı���Ϣ����ת����xml
						respXml = MessageUtil.messageToXml(textMessage);
					}
				}else if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){//��ע�¼�
					/*TextMessage textMessage = new TextMessage();
					textMessage.setToUserName(fromUserName);
					textMessage.setFromUserName(toUserName);
					textMessage.setCreateTime(new Date().getTime());
					textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					textMessage.setContent("��лС���ĵ����������Ѿ������ʱ��~~");
					// ���ı���Ϣ����ת����xml
					respXml = MessageUtil.messageToXml(textMessage);*/
					
					/**
					 * ���ÿͷ��ӿڽ��лظ���Ϣ
					 */
					CustomServiceUtil cc=new CustomServiceUtil();
					cc.sendTextMessageToUser("���ã���л����ע�ѽ����ѽ���ȫ���Ա���߳�Ϊ������\n��ѵ绰��4006109111", fromUserName);
					cc.sendTextMessageToUser(respContent, fromUserName);
				}else if(eventType.equals(MessageUtil.EVENT_TYPE_SCAN)){//ɨ���¼�
					String url = requestMap.get("EventKey");
					TextMessage textMessage = new TextMessage();
					textMessage.setToUserName(fromUserName);
					textMessage.setFromUserName(toUserName);
					textMessage.setCreateTime(new Date().getTime());
					textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					textMessage.setContent("��ɨ����"+url);
					// ���ı���Ϣ����ת����xml
					respXml = MessageUtil.messageToXml(textMessage);
				}else if(eventType.equals(MessageUtil.MESSAGE_VIEW)){//��תҳ���¼�
					String url = requestMap.get("EventKey");
					TextMessage textMessage = new TextMessage();
					textMessage.setToUserName(fromUserName);
					textMessage.setFromUserName(toUserName);
					textMessage.setCreateTime(new Date().getTime());
					textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					textMessage.setContent("��ת����"+url);
					// ���ı���Ϣ����ת����xml
					respXml = MessageUtil.messageToXml(textMessage);
				}
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)){//����λ���¼�
				String label = requestMap.get("Label");
				TextMessage textMessage = new TextMessage();
				textMessage.setToUserName(fromUserName);
				textMessage.setFromUserName(toUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
				textMessage.setContent("����λ��"+label);
				// ���ı���Ϣ����ת����xml
				respXml = MessageUtil.messageToXml(textMessage);
			}else{
				TextMessage textMessage = new TextMessage();
				textMessage.setToUserName(fromUserName);
				textMessage.setFromUserName(toUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
				textMessage.setContent("�ݲ�֧�ָ���Ϣ����");
				// ���ı���Ϣ����ת����xml
				respXml = MessageUtil.messageToXml(textMessage);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}
}