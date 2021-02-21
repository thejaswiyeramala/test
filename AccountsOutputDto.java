package com.wf.ibs.bootappsecure.dto;

public class AccountsOutputDto {

	private Long uci;
	private String accountType;
	private Long accountNum;
	private String status;
	private String dateCreated;
	
	public Long getUci() {
		return uci;
	}
	public void setUci(Long uci) {
		this.uci = uci;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Long getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(Long accountNum) {
		this.accountNum = accountNum;
	}
	
	
}
