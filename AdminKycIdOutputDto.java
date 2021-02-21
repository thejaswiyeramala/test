package com.wf.ibs.bootappsecure.dto;

import java.util.UUID;

public class AdminKycIdOutputDto {

	private Long kycId;
	private String firstName;
	private String lastName;
	private String contactNumber;
	private String emailID;
	private String nationalIDType;
	private String nationalIDNum;
	private String kycStatus;
	
	private Long uci;
	private String sysPassword;
		
	public Long getKycId() {
		return kycId;
	}
	public void setKycId(Long kycId) {
		this.kycId = kycId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getNationalIDType() {
		return nationalIDType;
	}
	public void setNationalIDType(String nationalIDType) {
		this.nationalIDType = nationalIDType;
	}
	public String getNationalIDNum() {
		return nationalIDNum;
	}
	public void setNationalIDNum(String nationalIDNum) {
		this.nationalIDNum = nationalIDNum;
	}
	public String getKycStatus() {
		return kycStatus;
	}
	public void setKycStatus(String kycStatus) {
		this.kycStatus = kycStatus;
	}
	public Long getUci() {
		return uci;
	}
	public void setUci(Long uci) {
		this.uci = uci;
	}
	public String getSysPassword() {
		return sysPassword;
	}
	public void setSysPassword(String sysPassword) {
		String temp=UUID.randomUUID().toString();
		sysPassword=temp.replace("-", "");
		this.sysPassword = sysPassword;
	}
	
}
