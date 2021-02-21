package com.wf.ibs.bootappsecure.dto;

import javax.validation.constraints.NotNull;

public class AdminKycIdInputDto {

	@NotNull(message="UCI is required")
	private Long kycId;

	public Long getKycId() {
		return kycId;
	}

	public void setKycId(Long kycId) {
		this.kycId = kycId;
	}
}
