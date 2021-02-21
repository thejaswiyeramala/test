package com.wf.ibs.bootappsecure.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wf.ibs.bootappsecure.dto.KYC;
import com.wf.ibs.bootappsecure.dto.KycDetailsInputDto;
import com.wf.ibs.bootappsecure.dto.KYCRegister;
import com.wf.ibs.bootappsecure.dto.KycDetailsOutputDto;
import com.wf.ibs.bootappsecure.exception.InvalidDataException;
import com.wf.ibs.bootappsecure.service.KycService;

@RequestMapping("/kyc")
@Controller
public class KYCController {
	
	// add dependency
	@Autowired
	private KycService service;
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String kycRegistration(Model model) {
		KYCRegister kycRegister = new KYCRegister();
		model.addAttribute("kycRegister", kycRegister);
		return "kyc-register";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String kycRegisterValidate(@Valid @ModelAttribute("kycRegister") KYCRegister kycRegister, BindingResult result) {
		if (result.hasErrors()) {
			return "kyc-register";
		}
		else
			return "redirect:/kyc/login";
	}
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String kycLogin(Model model) {
		KYC kyc = new KYC();
		model.addAttribute("kyc", kyc);
		return "kyc-login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String kycLoginValidate(@Valid @ModelAttribute("kyc") KYC kyc, BindingResult result) {
		
		if (result.hasErrors()) {
			return "kyc-login";
		}
		else
			return "redirect:/kyc/entry";
				
	}

	
	@RequestMapping(value="/entry", method=RequestMethod.GET)
	public String kycEntry(Model model) {
		KycDetailsInputDto kycDetailsInputDto = new KycDetailsInputDto();
		model.addAttribute("kycDetails", kycDetailsInputDto);
		return "kyc-home";			
		
	}
	
	// add KYC details
	@PostMapping("/entry")
	public String saveKycDetails(@Valid @ModelAttribute("kycDetails") KycDetailsInputDto kycDetailsInputDto,
								  BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "kyc-home";
		}
		
		KycDetailsOutputDto kycDetailsOutputDto = this.service.saveKycDetails(kycDetailsInputDto);
		if(kycDetailsOutputDto == null) {
			// throw custom exception
			throw new InvalidDataException("Invalid Data Format!");
		}
		
		model.addAttribute("kycDetails", kycDetailsOutputDto);
		return "kyc-confirm";
	}
		
}
