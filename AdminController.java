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

import com.wf.ibs.bootappsecure.dto.AdminKycIdInputDto;
import com.wf.ibs.bootappsecure.dto.AdminKycIdOutputDto;
import com.wf.ibs.bootappsecure.dto.ApplyCardInputDto;
import com.wf.ibs.bootappsecure.dto.ApplyCardOutputDto;
import com.wf.ibs.bootappsecure.dto.ApplyForLoanInputDto;
import com.wf.ibs.bootappsecure.dto.ApplyForLoanOutputDto;
import com.wf.ibs.bootappsecure.dto.CardUpgradationInputDto;
import com.wf.ibs.bootappsecure.dto.CardUpgradationOutputDto;
import com.wf.ibs.bootappsecure.dto.KycDetailsOutputDto;
import com.wf.ibs.bootappsecure.dto.ServiceProviderOutputDto;
import com.wf.ibs.bootappsecure.exception.AlreadyFinalizedException;
import com.wf.ibs.bootappsecure.exception.InvalidDataException;
import com.wf.ibs.bootappsecure.exception.KYCUserNotFoundException;
import com.wf.ibs.bootappsecure.exception.ServiceProviderNotFoundException;
import com.wf.ibs.bootappsecure.service.ApplyCardService;
import com.wf.ibs.bootappsecure.service.ApplyForLoanService;
import com.wf.ibs.bootappsecure.service.CardUpgradationService;
import com.wf.ibs.bootappsecure.service.CustomerService;
import com.wf.ibs.bootappsecure.service.KycService;
import com.wf.ibs.bootappsecure.service.SpService;


@RequestMapping("/admin")
@Controller
public class AdminController {
	
	// add dependency
	@Autowired
	private KycService kycService;
	@Autowired
	private CustomerService custService;
	@Autowired
	private SpService spService;
	@Autowired
	private ApplyForLoanService applyService;
	@Autowired
	private ApplyCardService applyCardService;
	@Autowired
	private CardUpgradationService cardUpgradationService;
		
	@RequestMapping("/home")
	public String adminHome() {
		
		return "admin-home";			
	}
	
		
	@RequestMapping(value="/getkyc-details", method=RequestMethod.GET)
	public String getKycDetails(Model model) {
		AdminKycIdInputDto kycInputDto = new AdminKycIdInputDto();
		model.addAttribute("getKycDetails", kycInputDto);
		return "admin-getKyc";			
		
	}
	
	//display KYC - approve KYC
	@PostMapping("/getkyc-details")
	public String editKyc(@Valid @ModelAttribute("getKycDetails") AdminKycIdInputDto kycInputDto,
								  BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "admin-getKyc";
		}
		
		KycDetailsOutputDto kycOutputDto = this.kycService.fetchSingleKycDetails(kycInputDto.getKycId());
		if(kycOutputDto == null) {
			// throw custom exception
			throw new KYCUserNotFoundException("KYC User not found with Id : " + kycInputDto.getKycId());
		}
		if((kycOutputDto.getKycStatus()).equalsIgnoreCase("Approved") || (kycOutputDto.getKycStatus()).equalsIgnoreCase("Declined")) {
			// throw custom exception
			throw new AlreadyFinalizedException("KYC Status has already been Finalized for the KYC ID: "+kycInputDto.getKycId());
		}
			
		model.addAttribute("getKycDetails", kycOutputDto);
		return "admin-editKyc";
	}
	
	//update KYC status
	@RequestMapping("/update-kycStatus")
	public String updatekycStatus(@ModelAttribute("getKycDetails") KycDetailsOutputDto kycOutputDto, 
								Model model) {
		KycDetailsOutputDto kycUpdatedOutputDto = this.kycService.updateKyc(kycOutputDto.getKycId(), 
													kycOutputDto);
		//If KYC Approved, UCI and sys password will be generated
		if ((kycUpdatedOutputDto.getKycStatus()).equalsIgnoreCase("Approved")) {
			AdminKycIdOutputDto adminKycIdOutputDto = this.custService.saveCustDetails(kycUpdatedOutputDto);
			if(adminKycIdOutputDto == null) {
				// throw custom exception
				throw new InvalidDataException("Invalid Data Format!");
			}
			model.addAttribute("kycUciDetails", adminKycIdOutputDto);
			return "admin-kycApproveConfirm";	
		}
		model.addAttribute("kycDetails", kycUpdatedOutputDto);
		return "admin-kycDeclineConfirm";			
		
	}

	@RequestMapping(value="/getsp-details", method=RequestMethod.GET)
	public String getSpDetails(Model model) {
		ServiceProviderOutputDto spDto = new ServiceProviderOutputDto();
		model.addAttribute("getSpDetails", spDto);
		return "admin-getSp";			
		
	}
	
	//display SP - approve SP
	@PostMapping("/getsp-details")
	public String editSp(@Valid @ModelAttribute("getSpDetails") ServiceProviderOutputDto spDto,
								  BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "admin-getSp";
		}
		
		ServiceProviderOutputDto spOutputDto = this.spService.fetchSingleSpDetails(spDto.getSpId());
		if(spOutputDto == null) {
			// throw custom exception
			throw new ServiceProviderNotFoundException("Service Provider not found with Id : " + spDto.getSpId());
		}
		if(spOutputDto.getSpStatus().equalsIgnoreCase("Approved") || spOutputDto.getSpStatus().equalsIgnoreCase("Declined")) {
			throw new AlreadyFinalizedException("Service Provider Status has already been Finalized for the SP ID: "+spDto.getSpId());
		}
			
		model.addAttribute("getSpDetails", spOutputDto);
		return "admin-editSp";
	}
	
	//update SP status
	@RequestMapping("/update-spStatus")
	public String updatespStatus(@ModelAttribute("getSpDetails") ServiceProviderOutputDto spOutputDto, 
								Model model) {
		ServiceProviderOutputDto spUpdatedOutputDto = this.spService.updateSp(spOutputDto);
		if(spUpdatedOutputDto == null) {
			// throw custom exception
			throw new InvalidDataException("Invalid Data Format!");
		}
		//If SP Approved
		if ((spUpdatedOutputDto.getSpStatus()).equalsIgnoreCase("Approved")) {
			//ServiceProviderOutputDto spFinalOutputDto = this.spService.joinAcctWithSp(spUpdatedOutputDto);
			model.addAttribute("getSpAcctDetails", spUpdatedOutputDto);
			return "admin-spApproveConfirm";	
		}
		model.addAttribute("getSpDetails", spUpdatedOutputDto);
		return "admin-spDeclineConfirm";			
		
	}

	@RequestMapping("/loansManagement") 
	public String loansManagement() {
		return"adminLoan";
	}
	@RequestMapping("/applyLoan") 
	public String applyLoan(Model model) {
		ApplyForLoanInputDto applyForLoanInputDto=new ApplyForLoanInputDto();
		model.addAttribute("applyForLoanInputDto", applyForLoanInputDto);	
		return "admin-getLoanDetails";
	}

	@RequestMapping("/getLoan-details")
	public String editLoan(@Valid @ModelAttribute("applyForLoanInputDto")ApplyForLoanInputDto applyForLoanInputDto,BindingResult result,Model model, Long id) {
		if(result.hasErrors()) {
			return "admin-getLoanDetails";
		}
		ApplyForLoanOutputDto applyForLoanOutputDto = this.applyService.fetchSingleDetail(id);
		System.out.println(id);
		model.addAttribute("applyForLoanInputDto", applyForLoanOutputDto);
		return "admin-editLoanDetails";
	}

	@RequestMapping("/detailsSave") 
	public String detailsSave(@Valid @ModelAttribute ApplyForLoanInputDto applyForLoanInputDto,BindingResult result,Model model, Long id) {
		ApplyForLoanOutputDto applyForLoanOutputDto = this.applyService.updateDetails(id, applyForLoanInputDto);
		  model.addAttribute("applyForLoanInputDto", applyForLoanOutputDto); 
		  return "admin-loanConfirmation"; 
		  }
	
	@RequestMapping("/cardsManagement") 
	public String cardsManagement() {
		return"adminCards";
	}
	@RequestMapping("/applyCard") 
	public String applyCard(Model model) {
		ApplyCardInputDto applyCardInputDto=new ApplyCardInputDto();
		model.addAttribute("applyCardInputDto", applyCardInputDto);
		return "admin-getCardDetails";
	}
	@RequestMapping("/getcard-details")
	public String editCard(@Valid @ModelAttribute("applyCardInputDto")ApplyCardInputDto applyCardInputDto,BindingResult result,Model model, Long id) {
		if(result.hasErrors()) {
			return "admin-getCardDetails";
		}
		ApplyCardOutputDto applyCardOutputDto = this.applyCardService.fetchSingleDetail(id);
		model.addAttribute("applyCardInputDto", applyCardOutputDto);
		return "admin-editCardDetails";
	}
	@RequestMapping("/carddetailsSave") 
	public String carddetailsSave(@Valid @ModelAttribute ApplyCardInputDto applyCardInputDto,BindingResult result,Model model, Long id) {
		ApplyCardOutputDto applyCardOutputDto = this.applyCardService.updateDetails(id, applyCardInputDto);
		  model.addAttribute("applyCardInputDto", applyCardOutputDto); 
		  return "admin-cardConfirmation"; 
		  }
	@RequestMapping("/cardUpgrade")
	public String cardUpgrade(Model model) {
		CardUpgradationInputDto cardUpgradationInputDto=new CardUpgradationInputDto();
		model.addAttribute("cardUpgradationInputDto", cardUpgradationInputDto);
		return "admin-getUpgradeCardDetails";
	}
	@RequestMapping("/getupgradecard-details")
	public String upgradeCard(@Valid @ModelAttribute("cardUpgradationInputDto")CardUpgradationInputDto cardUpgradationInputDto,BindingResult result,Model model, Long id) {
		if(result.hasErrors()) {
			return "admin-getUpgradeCardDetails";
		}
		CardUpgradationOutputDto cardUpgradationOutputDto = this.cardUpgradationService.fetchSingleDetail(id);
		model.addAttribute("cardUpgradationInputDto", cardUpgradationOutputDto);
		return "admin-editUpgradeCardDetails";
	}
	@RequestMapping("/cardUpgradeSave")
	public String cardUpgradeSave(@ModelAttribute CardUpgradationInputDto cardUpgradationInputDto,BindingResult result,Model model, Long id) {
		if(result.hasErrors()) {
			System.out.println(result);
			return "cardUpgradation";
		}
		cardUpgradationService.updateDetails(id, cardUpgradationInputDto);
		return "cardManagement_CustomerLogin";
	}
	}

