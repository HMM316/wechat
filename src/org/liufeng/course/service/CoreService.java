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
 * 核心服务类
 * 
 * @author liufeng
 * @date 2013-12-02
 */
public class CoreService {
	
	private static final String SOJN_URL = "http://www.sojn.com";
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return xml
	 */
	@SuppressWarnings("static-access")
	public static String processRequest(HttpServletRequest request) {
		// xml格式的消息数据
		String respXml = null;
		// 默认返回的文本消息内容
		String respContent = "感谢您使用搜金网微信服务，请输入对应的序号，查看相关信息:\n" +
							"       1、最新财经资讯\n" +
							"       2、理财产品推荐\n" +
							"       3、借贷产品推荐\n" +
							"       4、财经日历\n" +
							"       5、庄里大牌\n" +
							"       6、召  集  令\n" +
							"       7、石门金融\n" +
							"       8、庄里大咖\n" +
							"我们也的语音功能也正式上线啦~\n" +
							"发送‘？’查看主菜单";
		try {
			// 调用parseXml方法解析请求消息
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号
			String fromUserName = requestMap.get("FromUserName");
			// 开发者微信号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 消息创建时间
			String createTime = requestMap.get("CreateTime");
			
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				String content = requestMap.get("Content");
				if(!"?".equals(content)&&!"？".equals(content)&&!"1".equals(content)&&!"2".equals(content)&&!"3".equals(content)&&!"4".equals(content)&&!"5".equals(content)&&!"6".equals(content)&&!"7".equals(content)&&!"8".equals(content)&&!"s".equals(content)){
					respContent = ChatService.chat(fromUserName, createTime, content);
				}
				if("1".equals(content)){
			        // 回复图文消息
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(8);
					List<Article> articles =new ArrayList<Article>();
					Document doc = Jsoup.connect("http://www.sojn.com/cjzx/listlatestNews.html").get();//链接搜金网获取最新新闻
			        Elements li_data = doc.getElementsByTag("li");										//解析html 装载数据
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
					// 将文本消息对象转换成xml
					respXml = MessageUtil.messageToXml(newsMessage);
				}else if("2".equals(content)){
					  // 回复图文消息
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(8);
					List<Article> articles =new ArrayList<Article>();
					Document doc = Jsoup.connect("http://www.sojn.com/lccp/lccprank.html?i=1").get();//链接搜金网获取最新新闻
			        Elements li_data = doc.getElementsByTag("li");										//解析html 装载数据
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
					// 将文本消息对象转换成xml
					respXml = MessageUtil.messageToXml(newsMessage);
				}else if("3".equals(content)){
					// 回复图文消息
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(7);
					List<Article> articles =new ArrayList<Article>();
					JsonUtil json=new JsonUtil();
					JSONArray arr= json.getJson("http://www.sojn.com/lccp/isrecommended.html");//链接搜金网获取推荐借贷产品
					//解析json 装载数据
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
					// 将文本消息对象转换成xml
					respXml = MessageUtil.messageToXml(newsMessage);
				}else if("4".equals(content)){
					// 回复图文消息
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(1);
					List<Article> articles =new ArrayList<Article>();
					JsonUtil json=new JsonUtil();
					JSONObject jsonObject= json.getJsonPost("http://www.sojn.com/cjzx/indexNews.html?location=N33");//链接搜金网获取推荐借贷产品
					//解析json 装载数据
			        Article article=new Article();
			        JSONObject jsonObject2=JSONObject.fromObject(jsonObject.get("newsPic"));
					article.setUrl("http://m.sojn.com/frontNews/tonewsInfo.html?id="+jsonObject2.get("newID"));
					article.setTitle(jsonObject2.get("newTitle").toString());
					article.setPicUrl(SOJN_URL+jsonObject.get("pic").toString());
					article.setDescription(jsonObject2.get("newSummary").toString());
					articles.add(article);
					newsMessage.setArticles(articles);
					// 将文本消息对象转换成xml
					respXml = MessageUtil.messageToXml(newsMessage);
				}else if("5".equals(content)){
					// 回复图文消息
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(1);
					List<Article> articles =new ArrayList<Article>();
					JsonUtil json=new JsonUtil();
					JSONObject jsonObject= json.getJsonPost("http://www.sojn.com/cjzx/indexNews.html?location=N35");//链接搜金网获取推荐借贷产品
					//解析json 装载数据
			        Article article=new Article();
			        JSONObject jsonObject2=JSONObject.fromObject(jsonObject.get("newsPic"));
					article.setUrl("http://m.sojn.com/frontNews/tonewsInfo.html?id="+jsonObject2.get("newID"));
					article.setTitle(jsonObject2.get("newTitle").toString());
					article.setPicUrl(SOJN_URL+jsonObject.get("pic").toString());
					article.setDescription(jsonObject2.get("newSummary").toString());
					articles.add(article);
					newsMessage.setArticles(articles);
					// 将文本消息对象转换成xml
					respXml = MessageUtil.messageToXml(newsMessage);
				}else if("6".equals(content)){
					// 回复图文消息
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(1);
					List<Article> articles =new ArrayList<Article>();
					JsonUtil json=new JsonUtil();
					JSONObject jsonObject= json.getJsonPost("http://www.sojn.com/cjzx/indexNews.html?location=N37");//链接搜金网获取推荐借贷产品
					//解析json 装载数据
			        Article article=new Article();
			        JSONObject jsonObject2=JSONObject.fromObject(jsonObject.get("newsPic"));
					article.setUrl("http://m.sojn.com/frontNews/tonewsInfo.html?id="+jsonObject2.get("newID"));
					article.setTitle(jsonObject2.get("newTitle").toString());
					article.setPicUrl(SOJN_URL+jsonObject.get("pic").toString());
					article.setDescription(jsonObject2.get("newSummary").toString());
					articles.add(article);
					newsMessage.setArticles(articles);
					// 将文本消息对象转换成xml
					respXml = MessageUtil.messageToXml(newsMessage);
				}else if("7".equals(content)){
					// 回复图文消息
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(2);
					List<Article> articles =new ArrayList<Article>();
					JsonUtil json=new JsonUtil();
					JSONObject jsonObject= json.getJsonPost("http://www.sojn.com/cjzx/indexNews.html?location=N38-1");//链接搜金网获取推荐借贷产品
					//解析json 装载数据
			        Article article=new Article();
			        JSONObject jsonObject2=JSONObject.fromObject(jsonObject.get("newsPic"));
					article.setUrl("http://m.sojn.com/frontNews/tonewsInfo.html?id="+jsonObject2.get("newID"));
					article.setTitle(jsonObject2.get("newTitle").toString());
					article.setPicUrl(SOJN_URL+jsonObject.get("pic").toString());
					article.setDescription(jsonObject2.get("newSummary").toString());
					articles.add(article);
					
					JSONObject jsonObjectN38_2= json.getJsonPost("http://www.sojn.com/cjzx/indexNews.html?location=N38-2");//链接搜金网获取推荐借贷产品
					//解析json 装载数据
					Article article2=new Article();
					JSONObject jsonObjectN38_2_2=JSONObject.fromObject(jsonObjectN38_2.get("newsPic"));
					article2.setUrl("http://m.sojn.com/frontNews/tonewsInfo.html?id="+jsonObjectN38_2_2.get("newID"));
					article2.setTitle(jsonObjectN38_2_2.get("newTitle").toString());
					article2.setPicUrl(SOJN_URL+jsonObjectN38_2.get("pic").toString());
					article2.setDescription(jsonObjectN38_2_2.get("newSummary").toString());
					articles.add(article2);
					newsMessage.setArticles(articles);
					// 将文本消息对象转换成xml
					respXml = MessageUtil.messageToXml(newsMessage);
				}else if("8".equals(content)){
					// 回复图文消息
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(2);
					List<Article> articles =new ArrayList<Article>();
					JsonUtil json=new JsonUtil();
					JSONObject jsonObject= json.getJsonPost("http://www.sojn.com/cjzx/indexNews.html?location=N39-1");//链接搜金网获取推荐借贷产品
					//解析json 装载数据
			        Article article=new Article();
			        JSONObject jsonObject2=JSONObject.fromObject(jsonObject.get("newsPic"));
					article.setUrl("http://m.sojn.com/frontNews/tonewsInfo.html?id="+jsonObject2.get("newID"));
					article.setTitle(jsonObject2.get("newTitle").toString());
					article.setPicUrl(SOJN_URL+jsonObject.get("pic").toString());
					article.setDescription(jsonObject2.get("newSummary").toString());
					articles.add(article);
					
					JSONObject jsonObjectN39_2= json.getJsonPost("http://www.sojn.com/cjzx/indexNews.html?location=N39-2");//链接搜金网获取推荐借贷产品
					//解析json 装载数据
					Article article2=new Article();
					JSONObject jsonObjectN39_2_2=JSONObject.fromObject(jsonObjectN39_2.get("newsPic"));
					article2.setUrl("http://m.sojn.com/frontNews/tonewsInfo.html?id="+jsonObjectN39_2_2.get("newID"));
					article2.setTitle(jsonObjectN39_2_2.get("newTitle").toString());
					article2.setPicUrl(SOJN_URL+jsonObjectN39_2.get("pic").toString());
					article2.setDescription(jsonObjectN39_2_2.get("newSummary").toString());
					articles.add(article2);
					newsMessage.setArticles(articles);
					// 将文本消息对象转换成xml
					respXml = MessageUtil.messageToXml(newsMessage);
				}else if("s".equals(content)){
					TextMessage textMessage = new TextMessage();
					textMessage.setToUserName(fromUserName);
					textMessage.setFromUserName(toUserName);
					textMessage.setCreateTime(new Date().getTime());
					textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					textMessage.setContent("该功能正在开发中，感谢您的支持与理解。\n点击进入<a href='http://m.sojn.com/weixin/sign.html'>搜金网</a>");
					//textMessage.setContent("该功能正在开发中，感谢您的支持与理解。\n点击进入<a href='http://m.sojn.com/Mydemo'>搜金网</a>");
					// 将文本消息对象转换成xml
					respXml = MessageUtil.messageToXml(textMessage);
				}else{
					// 回复文本消息
					TextMessage textMessage = new TextMessage();
					textMessage.setToUserName(fromUserName);
					textMessage.setFromUserName(toUserName);
					textMessage.setCreateTime(new Date().getTime());
					textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					textMessage.setContent(respContent);
					// 将文本消息对象转换成xml
					respXml = MessageUtil.messageToXml(textMessage);
				}
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
				String Recognition = requestMap.get("Recognition");
				if(!"".equals(Recognition)){
					/**
					 * 通过识别的语音内容进行回复
					 */
					respContent = ChatService.chat(fromUserName, createTime, Recognition);
				}
				/**
				 * 调用客服接口进行回复消息
				 */
				CustomServiceUtil cc=new CustomServiceUtil();
				//cc.sendTextMessageToUser("您发送的语音消息是："+Recognition, fromUserName);
				cc.sendTextMessageToUser(respContent, fromUserName);
			}else if(msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_IMAGE)){
				// 回复图文消息
				NewsMessage newsMessage = new NewsMessage();
				newsMessage.setToUserName(fromUserName);
				newsMessage.setFromUserName(toUserName);
				newsMessage.setCreateTime(new Date().getTime());
				newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setArticleCount(3);
				List<Article> articles =new ArrayList<Article>();
				Article article=new Article();
				article.setDescription("这里是描述信息。");
				article.setUrl("http://www.baidu.com");
				article.setTitle("这里是标题");
				article.setPicUrl("http://ming.ngrok.cc/Mydemo/image/sd.jpg");
				
				Article article2=new Article();
				article2.setDescription("这里是描述信息。");
				article2.setUrl("http://www.baidu.com");
				article2.setTitle("这里是标题");
				article2.setPicUrl("http://ming.ngrok.cc/Mydemo/image/sd.jpg");
				
				Article article3=new Article();
				article3.setDescription("这里是描述信息。");
				article3.setUrl("http://www.baidu.com");
				article3.setTitle("这里是标题");
				article3.setPicUrl("http://ming.ngrok.cc/Mydemo/image/sd.jpg");
				
				articles.add(article);
				articles.add(article2);
				articles.add(article3);
				newsMessage.setArticles(articles);
				// 将文本消息对象转换成xml
				respXml = MessageUtil.messageToXml(newsMessage);
			}else if((msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT))){
				String eventType = requestMap.get("Event");

				if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){			//普通点击事件
					String key = requestMap.get("EventKey");
					if("11".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("该功能正在开发中，感谢您的支持与理解。您可以先和小金聊会天，支持语音哒~");
						// 将文本消息对象转换成xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("12".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("该功能正在开发中，感谢您的支持与理解。您可以先和小金聊会天，支持语音哒~");
						// 将文本消息对象转换成xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("13".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("该功能正在开发中，感谢您的支持与理解。您可以先和小金聊会天，支持语音哒~");
						// 将文本消息对象转换成xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("14".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("该功能正在开发中，感谢您的支持与理解。您可以先和小金聊会天，支持语音哒~");
						// 将文本消息对象转换成xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("15".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("该功能正在开发中，感谢您的支持与理解。您可以先和小金聊会天，支持语音哒~");
						// 将文本消息对象转换成xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("21".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("该功能正在开发中，感谢您的支持与理解。您可以先和小金聊会天，支持语音哒~");
						// 将文本消息对象转换成xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("22".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("该功能正在开发中，感谢您的支持与理解。您可以先和小金聊会天，支持语音哒~");
						// 将文本消息对象转换成xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("23".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("该功能正在开发中，感谢您的支持与理解。您可以先和小金聊会天，支持语音哒~");
						// 将文本消息对象转换成xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("24".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("该功能正在开发中，感谢您的支持与理解。您可以先和小金聊会天，支持语音哒~");
						// 将文本消息对象转换成xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("25".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("该功能正在开发中，感谢您的支持与理解。您可以先和小金聊会天，支持语音哒~");
						// 将文本消息对象转换成xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("31".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("该功能正在开发中，感谢您的支持与理解。\n点击进入<a href='http://www.sojn.com'>搜金网</a>");
						// 将文本消息对象转换成xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("32".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("该功能正在开发中，感谢您的支持与理解。\n点击进入<a href='http://www.sojn.com'>搜金网</a>");
						// 将文本消息对象转换成xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("33".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("该功能正在开发中，感谢您的支持与理解。\n点击进入<a href='http://www.sojn.com'>搜金网</a>");
						// 将文本消息对象转换成xml
						respXml = MessageUtil.messageToXml(textMessage);
					}else if("34".equals(key)){///
						  // 回复图文消息
						NewsMessage newsMessage = new NewsMessage();
						newsMessage.setToUserName(fromUserName);
						newsMessage.setFromUserName(toUserName);
						newsMessage.setCreateTime(new Date().getTime());
						newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						newsMessage.setArticleCount(3);
						List<Article> articles =new ArrayList<Article>();
						Document doc = Jsoup.connect("http://www.sojn.com/forum/newhdsj.html?BBSType=278&limit=3").get();//链接搜金网获取最新新闻
				        Elements li_data = doc.getElementsByTag("li");										//解析html 装载数据
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
						// 将文本消息对象转换成xml
						respXml = MessageUtil.messageToXml(newsMessage);
					}else if("35".equals(key)){
						TextMessage textMessage = new TextMessage();
						textMessage.setToUserName(fromUserName);
						textMessage.setFromUserName(toUserName);
						textMessage.setCreateTime(new Date().getTime());
						textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						textMessage.setContent("该功能正在开发中，感谢您的支持与理解。\n点击进入<a href='http://www.sojn.com'>搜金网</a>");
						// 将文本消息对象转换成xml
						respXml = MessageUtil.messageToXml(textMessage);
					}
				}else if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){//关注事件
					/*TextMessage textMessage = new TextMessage();
					textMessage.setToUserName(fromUserName);
					textMessage.setFromUserName(toUserName);
					textMessage.setCreateTime(new Date().getTime());
					textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					textMessage.setContent("感谢小主的到来，我们已经恭候多时了~~");
					// 将文本消息对象转换成xml
					respXml = MessageUtil.messageToXml(textMessage);*/
					
					/**
					 * 调用客服接口进行回复消息
					 */
					CustomServiceUtil cc=new CustomServiceUtil();
					cc.sendTextMessageToUser("您好，感谢您关注搜金网搜金网全体成员将竭诚为您服务。\n免费电话：4006109111", fromUserName);
					cc.sendTextMessageToUser(respContent, fromUserName);
				}else if(eventType.equals(MessageUtil.EVENT_TYPE_SCAN)){//扫码事件
					String url = requestMap.get("EventKey");
					TextMessage textMessage = new TextMessage();
					textMessage.setToUserName(fromUserName);
					textMessage.setFromUserName(toUserName);
					textMessage.setCreateTime(new Date().getTime());
					textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					textMessage.setContent("您扫码了"+url);
					// 将文本消息对象转换成xml
					respXml = MessageUtil.messageToXml(textMessage);
				}else if(eventType.equals(MessageUtil.MESSAGE_VIEW)){//跳转页面事件
					String url = requestMap.get("EventKey");
					TextMessage textMessage = new TextMessage();
					textMessage.setToUserName(fromUserName);
					textMessage.setFromUserName(toUserName);
					textMessage.setCreateTime(new Date().getTime());
					textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					textMessage.setContent("跳转链接"+url);
					// 将文本消息对象转换成xml
					respXml = MessageUtil.messageToXml(textMessage);
				}
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)){//地理位置事件
				String label = requestMap.get("Label");
				TextMessage textMessage = new TextMessage();
				textMessage.setToUserName(fromUserName);
				textMessage.setFromUserName(toUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
				textMessage.setContent("地理位置"+label);
				// 将文本消息对象转换成xml
				respXml = MessageUtil.messageToXml(textMessage);
			}else{
				TextMessage textMessage = new TextMessage();
				textMessage.setToUserName(fromUserName);
				textMessage.setFromUserName(toUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
				textMessage.setContent("暂不支持该消息类型");
				// 将文本消息对象转换成xml
				respXml = MessageUtil.messageToXml(textMessage);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}
}