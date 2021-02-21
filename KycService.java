package com.wf.ibs.bootappsecure.service;

import com.wf.ibs.bootappsecure.dto.KycDetailsInputDto;
import com.wf.ibs.bootappsecure.dto.KycDetailsOutputDto;

public interface KycService {

	public KycDetailsOutputDto fetchSingleKycDetails(Long KycId);
	public KycDetailsOutputDto saveKycDetails(KycDetailsInputDto kycDetailsInputDto);
	public KycDetailsOutputDto updateKyc(Long KycId, KycDetailsOutputDto kycDetailsOutputDto);	
}
