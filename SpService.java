package com.wf.ibs.bootappsecure.service;

import java.util.List;

import com.wf.ibs.bootappsecure.dto.ServiceProviderInputDto;
import com.wf.ibs.bootappsecure.dto.ServiceProviderOutputDto;

public interface SpService {

	public ServiceProviderOutputDto saveSpDetails(ServiceProviderInputDto spInputDto);
	public List<ServiceProviderOutputDto> fetchAllServiceProv();
	public List<String> fetchSPNames();
	public ServiceProviderOutputDto fetchSingleSpDetails(Long SpId);
	public ServiceProviderOutputDto updateSp(ServiceProviderOutputDto spOutputDto);
	//public ServiceProviderOutputDto joinAcctWithSp(ServiceProviderOutputDto spOutputDto);	
}
