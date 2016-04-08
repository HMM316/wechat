package org.liufeng.course.pojo;
/**
 * Js_AccessToken
 * @author Administrator
 *
 */
public class Js_AccessToken {
	private Integer errcode;	//0
	private String errmsg;		//ok
	private String ticket;		//kgt8ON7yVITDhtdwci0qeV5pyklV4ZZXVQOgZ04NcsVlmuUDJNQzLEbQZE9pqT3OoqxAFNTzErShyvcCL4UxLw
	private Integer expires_in;	//7200
	
	public Integer getErrcode() {
		return errcode;
	}
	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public Integer getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}
}
