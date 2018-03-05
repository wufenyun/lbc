package com.lbc.test.module.user.entity;

import java.util.Date;


public class UserEntity {
	
	private Integer id;
	private String name;
	private String address;
	private String phone;
	private String mail;
	private Date insertTime;
	private Date updateIime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Date getUpdateIime() {
		return updateIime;
	}
	public void setUpdateIime(Date updateIime) {
		this.updateIime = updateIime;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
}
