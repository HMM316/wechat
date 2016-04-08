package org.liufeng.course.util;

import org.liufeng.course.service.ChatService;

public class CreateIndex {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		ChatService ct=new ChatService();
		ct.createIndex();
	}
}
