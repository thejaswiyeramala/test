package com.wf.ibs.bootappsecure.dto;

import javax.validation.constraints.NotNull;

public class AccountsInputDto {

	@NotNull(message="UCI is required")
	private Long uci;
	
	private String accountType;

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Long getUci() {
		return uci;
	}

	public void setUci(Long uci) {
		this.uci = uci;
	}
	
}
