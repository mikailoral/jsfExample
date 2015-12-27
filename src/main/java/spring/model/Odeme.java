package spring.model;

import java.math.BigInteger;

public class Odeme {
	
	private String user;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private String method;
	private String total;
	private String price;
	private String lectures;
	private BigInteger orderid;
	private String metavalue;

	public Odeme() {
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getLectures() {
		return lectures;
	}
	public void setLectures(String lectures) {
		this.lectures = lectures;
	}
	public BigInteger getOrderid() {
		return orderid;
	}
	public void setOrderid(BigInteger orderid) {
		this.orderid = orderid;
	}
	public String getMetavalue() {
		return metavalue;
	}
	public void setMetavalue(String metavalue) {
		this.metavalue = metavalue;
	}	
}
