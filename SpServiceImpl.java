package com.wf.ibs.bootappsecure.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wf.ibs.bootappsecure.dto.ServiceProviderInputDto;
import com.wf.ibs.bootappsecure.dto.ServiceProviderOutputDto;
import com.wf.ibs.bootappsecure.entity.ServiceProvider;
import com.wf.ibs.bootappsecure.repository.ServiceProviderRepository;
import com.wf.ibs.bootappsecure.service.SpService;

@Service
public class SpServiceImpl implements SpService {

	//injecting a dependency
	@Autowired
	private ServiceProviderRepository repository;
	
	@Override
	public ServiceProviderOutputDto saveSpDetails(ServiceProviderInputDto spInputDto) {
		// convert dto into entity
		ServiceProvider sp = this.convertInputDtoToEntity(spInputDto);
		// save into DB, returns newly added record
		ServiceProvider newSp = this.repository.save(sp);
		// convert entity into dto
		ServiceProviderOutputDto spOutputDto =  this.convertEntityToOutputDto(newSp);
		return spOutputDto;
	}

	@Override
	public List<ServiceProviderOutputDto> fetchAllServiceProv() {
		List<ServiceProvider> servProvs = this.repository.findAll();
		//convert entity to dto list
		List<ServiceProviderOutputDto> spDtos = new ArrayList<ServiceProviderOutputDto>();
		for(ServiceProvider sp:servProvs) {
			ServiceProviderOutputDto spDto = this.convertEntityToOutputDto(sp);
			spDtos.add(spDto);
		}
			
		return spDtos;
	}

	@Override
	public List<String> fetchSPNames() {
		List<ServiceProvider> servProvs = this.repository.findAll();
		
		List<String> spList = new ArrayList<String>();
		for(ServiceProvider sp:servProvs) {
			String spName = this.convertSPRecordToSPName(sp);
			spList.add(spName);
		}
		return spList;
	}

	@Override
	public ServiceProviderOutputDto fetchSingleSpDetails(Long SpId) {
		if(this.repository.existsById(SpId)) {
			ServiceProvider sp = this.repository.findById(SpId).orElse(null);
			ServiceProviderOutputDto spOutputDto = this.convertEntityToOutputDto(sp);
			return spOutputDto;
		}
		return null;
	}
	
	@Override
	public ServiceProviderOutputDto updateSp(ServiceProviderOutputDto spOutputDto) {
		if(this.repository.existsById(spOutputDto.getSpId())) {
			// convert input dto into entity
			ServiceProvider sp = this.convertOutputDtoToEntity(spOutputDto);
			sp.setSpId(spOutputDto.getSpId());
			ServiceProvider newSp = this.repository.save(sp);
			
			ServiceProviderOutputDto spNewOutputDto = this.convertEntityToOutputDto(newSp);
			return spNewOutputDto;
		}
		return null;
	}
	
	
	//utility method
	private ServiceProvider convertInputDtoToEntity(ServiceProviderInputDto spInputDto) {
		ServiceProvider sp = new ServiceProvider();
		sp.setFirstName(spInputDto.getFirstName());
		sp.setLastName(spInputDto.getLastName());
		sp.setEmailID(spInputDto.getEmailID());
		sp.setContactNumber(spInputDto.getContactNumber());
		sp.setAccountNumber(spInputDto.getAccountNumber());
		sp.setBranchName(spInputDto.getBranchName());
		sp.setBranchAddress(spInputDto.getBranchAddress());
		sp.setIfscCode(spInputDto.getIfscCode());
		sp.setSpStatus("Submitted");
			
		return sp;
	}
	
	// utility method
	private ServiceProviderOutputDto convertEntityToOutputDto(ServiceProvider sp) {
		ServiceProviderOutputDto spOutputDto = new ServiceProviderOutputDto();
		
		spOutputDto.setSpId(sp.getSpId());
		spOutputDto.setFirstName(sp.getFirstName());
		spOutputDto.setLastName(sp.getLastName());
		spOutputDto.setEmailID(sp.getEmailID());
		spOutputDto.setAccountNumber(sp.getAccountNumber());
		spOutputDto.setContactNumber(sp.getContactNumber());
		spOutputDto.setBranchName(sp.getBranchName());
		spOutputDto.setBranchAddress(sp.getBranchAddress());
		spOutputDto.setIfscCode(sp.getIfscCode());
		spOutputDto.setSpStatus(sp.getSpStatus());
					
		return spOutputDto;
	}
	
	// utility method
	private String convertSPRecordToSPName(ServiceProvider sp) {
		String spname;
		spname=sp.getFirstName()+" "+sp.getLastName();
		return spname;
	}
	
	private ServiceProvider convertOutputDtoToEntity(ServiceProviderOutputDto spOutputDto) {
		ServiceProvider sp = new ServiceProvider();
		sp.setSpId(spOutputDto.getSpId());
		sp.setFirstName(spOutputDto.getFirstName());
		sp.setLastName(spOutputDto.getLastName());
		sp.setEmailID(spOutputDto.getEmailID());
		sp.setAccountNumber(spOutputDto.getAccountNumber());
		sp.setContactNumber(spOutputDto.getContactNumber());
		sp.setBranchName(spOutputDto.getBranchName());
		sp.setBranchAddress(spOutputDto.getBranchAddress());
		sp.setIfscCode(spOutputDto.getIfscCode());
		sp.setSpStatus(spOutputDto.getSpStatus());
			
		return sp;
	}

	
	
}
