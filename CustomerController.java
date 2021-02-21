package com.wf.ibs.bootappsecure.controller;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wf.ibs.bootappsecure.dto.*;
import com.wf.ibs.bootappsecure.exception.AccountNotFoundException;
import com.wf.ibs.bootappsecure.exception.CustomerNotFoundException;
import com.wf.ibs.bootappsecure.exception.InvalidDataException;
import com.wf.ibs.bootappsecure.service.*;

@RequestMapping("/customer")
@Controller
public class CustomerController {

	// add dependency
	@Autowired
	private SpService spService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AccountsService acctsService;
	@Autowired
	private TransactionsService txnService;
	@Autowired
	private BeneficiaryService benService;
	@Autowired
	private ApplyForLoanService applyService;
	@Autowired
	private PayEMIForLoanService payEMIService;
	@Autowired
	private ApplyCardService applyCardService;
	@Autowired
	private CardUpgradationService cardUpgradationService;
	@Autowired
	private ResetPinService resetPinService;
	
	@RequestMapping(value="/ibslogin", method=RequestMethod.GET)
	public String ibsLogin(Model model) {
		CustomerInputDto custInputDto = new CustomerInputDto();
		model.addAttribute("ibsLogin", custInputDto);
		return "ibs-login";
	}
	
		
	@RequestMapping(value="/ibslogin", method=RequestMethod.POST)
	public String ibsLoginValidate(@Valid @ModelAttribute("ibsLogin") CustomerInputDto custInputDto, 
									BindingResult result,
									Model model) {
		if (result.hasErrors()) 
			return "ibs-login";
				
		CustomerOutputDto custOutputDto = this.customerService.fetchSingleCustomer(custInputDto.getUci());
		
		if(custInputDto.getPassword().equals(custOutputDto.getNewPassword())) 
			return "ibs-home";
		
		if(custInputDto.getPassword().equals(custOutputDto.getSysPassword())) 
			return "redirect:/customer/reset-pwd";
		
		return "loginerror";
	}
	
	@RequestMapping(value="/reset-pwd", method=RequestMethod.GET)
	public String resetPwd(Model model) {
		IBSResetPwdInputDto resetPwdInputDto = new IBSResetPwdInputDto();
		model.addAttribute("resetPwd", resetPwdInputDto);
		return "ibs-resetpwd";			
		
	}
	
	@RequestMapping(value="/reset-pwd", method=RequestMethod.POST)
	public String resetPwdValidate(@Valid @ModelAttribute("resetPwd") IBSResetPwdInputDto resetPwdInputDto, 
									BindingResult result,
									Model model) {
		if (result.hasErrors()) 
			return "ibs-resetpwd";
		CustomerOutputDto custOutputDto = this.customerService.updatePwd(resetPwdInputDto.getUci(), 
											resetPwdInputDto);
		if(custOutputDto == null) {
			// throw custom exception
			throw new CustomerNotFoundException("Customer not found with Id : " + resetPwdInputDto.getUci());
		}
		
		return "ibs-resetpwdconfirm";
	}
	
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String ibsHome(Model model) {
		IBSHome ibsHome = new IBSHome();
		model.addAttribute("ibsHome", ibsHome);
		return "ibs-home";			
	}
	
	
	@RequestMapping("/acct-summary")
	public String acctSummary() {
		return "acct-summary";
	}
	
	@RequestMapping(value="/create-acct", method=RequestMethod.GET)
	public String acctCreate(Model model) {
		AccountsInputDto acctsInputDto = new AccountsInputDto();
		model.addAttribute("acctCreate", acctsInputDto);
		return "account-create";
	}
	
	@RequestMapping(value="/create-acct", method=RequestMethod.POST)
	public String acctCreateConfirm(@Valid @ModelAttribute("acctCreate") AccountsInputDto acctsInputDto, 
									BindingResult result,
									Model model) throws ParseException {
		
		if (result.hasErrors()) {
			return "account-create";
		}
		
		AccountsOutputDto acctsOutputDto = this.acctsService.saveAcctDetails(acctsInputDto);
		if(acctsOutputDto == null) {
			// throw custom exception
			throw new CustomerNotFoundException("Customer not found with Id : " + acctsInputDto.getUci());
		}
		model.addAttribute("acctCreate", acctsOutputDto);
		return "account-confirm";
			
	}
	
	@RequestMapping(value="/check-balance", method=RequestMethod.GET)
	public String chkBalance(Model model) {
		BalanceInputDto balInputDto = new BalanceInputDto();
		model.addAttribute("chkBalance", balInputDto);
		return "check-balance";
	}
	
	@RequestMapping(value="/check-balance", method=RequestMethod.POST)
	public String chkBalanceConfirm(@Valid @ModelAttribute("chkBalance") BalanceInputDto balInputDto, 
									BindingResult result,
									Model model) {
		
		if (result.hasErrors()) {
			return "check-balance";
		}
		
		BalanceOutputDto balOutputDto = this.acctsService.fetchAcctBalance(balInputDto.getAccountNumber());
		if(balOutputDto == null) {
			// throw custom exception
			throw new AccountNotFoundException("Account not found with Id : " + balInputDto.getAccountNumber());
		}
		model.addAttribute("chkBalance", balOutputDto);
		return "confirm-balance";
			
	}
	
	@RequestMapping(value="/request-stmt", method=RequestMethod.GET)
	public String requestStmt(Model model) {
		StatementInputDto stmtInputDto = new StatementInputDto();
		model.addAttribute("reqStatement", stmtInputDto);
		return "request-stmt";
	}
	
	@RequestMapping(value="/request-stmt", method=RequestMethod.POST)
	public String requestStmtConfirm(@Valid @ModelAttribute("reqStatement") StatementInputDto stmtInputDto, 
										BindingResult result,
										Model model) throws ParseException {
		
		if (result.hasErrors()) {
			return "request-stmt";
		}
				
		List<StatementOutputDto> stmtOutputDtos = this.txnService.fetchStatement(stmtInputDto);
		if(stmtOutputDtos == null) {
			// throw custom exception
			throw new AccountNotFoundException("Account not found with Id : " + stmtInputDto.getAcctNum());
		}
		model.addAttribute("reqStatement", stmtOutputDtos);
		return "requestStmt-confirm";
	}
	
	@RequestMapping(value="/transfer-funds", method=RequestMethod.GET)
	public String transferFunds(Model model) {
		FundsTransferInputDto fundsInputDto = new FundsTransferInputDto();
		List<String> beneficiaries = this.benService.fetchBenefNames();
		fundsInputDto.setBeneficiary(beneficiaries);;
		model.addAttribute("fundsTransfer", fundsInputDto);
		return "funds-transfer";
	}
	
	@RequestMapping(value="/transfer-funds", method=RequestMethod.POST)
	public String transferFundsSuccess(@Valid @ModelAttribute("fundsTransfer") FundsTransferInputDto fundsInputDto, 
										BindingResult result,
										Model model) throws ParseException {
		
		if (result.hasErrors()) {
			return "funds-transfer";
		}
		
		FundsTransferOutputDto fundsOutputDto = this.txnService.saveFundTxnDetails(fundsInputDto);
		if(fundsOutputDto == null) {
			// throw custom exception
			throw new AccountNotFoundException("Account not found with Id : " + fundsInputDto.getAcctNum());
		}
		model.addAttribute("fundsTransfer", fundsOutputDto);
		return "fundsTransfer-confirm";
	}
	
	@RequestMapping(value="/bill-payments", method=RequestMethod.GET)
	public String billPayments(Model model) {
		BillPaymentsInputDto billPaymentsInputDto = new BillPaymentsInputDto();
		List<String> servProvs = this.spService.fetchSPNames();
		billPaymentsInputDto.setServProvider(servProvs);		
		model.addAttribute("billPayments", billPaymentsInputDto);
		return "bill-payments";
	}
	
	@RequestMapping(value="/bill-payments", method=RequestMethod.POST)
	public String billPaymentSuccess(@Valid @ModelAttribute("billPayments") BillPaymentsInputDto billPaymentsInputDto, 
										BindingResult result,
										Model model) throws ParseException {
		
		if (result.hasErrors()) {
			return "bill-payments";
		}
		
		BillPaymentsOutputDto billOutputDto = this.txnService.saveBillTxnDetails(billPaymentsInputDto);
		if(billOutputDto == null) {
			// throw custom exception
			throw new AccountNotFoundException("Account not found with Id : " + billPaymentsInputDto.getAcctNum());
		}
		model.addAttribute("billPayments", billOutputDto);
		return "billPayments-confirm";
	}
	
	@RequestMapping(value="/service-prov", method=RequestMethod.GET)
	public String serviceProviderEntry(Model model) {
		ServiceProviderInputDto serviceProv = new ServiceProviderInputDto();
		model.addAttribute("serviceProv", serviceProv);
		return "servProv-entry";
	}
	
	//Register Service Provider details
	@PostMapping(value="/service-prov")
	public String serviceProvConfirm(@Valid @ModelAttribute("serviceProv") ServiceProviderInputDto spInputDto,
									 BindingResult result,
									 Model model) {
		
		if (result.hasErrors()) {
			return "servProv-entry";
		}
		
		ServiceProviderOutputDto spOutputDto = this.spService.saveSpDetails(spInputDto);
		if(spOutputDto == null) {
			// throw custom exception
			throw new InvalidDataException("Invalid Data Format!");
		}
		model.addAttribute("serviceProv", spOutputDto);
		return "servProv-confirm";
				
	}

	@RequestMapping("/beneficiaryhome") 
	public String beneficiaryhome(Model model) {
		BeneficiaryInputDto beneficiaryInputDto=new BeneficiaryInputDto();
		model.addAttribute("beneficiaryInputDto", beneficiaryInputDto);		
		return "beneficiary-home";
	}
	
	@RequestMapping("/addDetails") 
	public String addDetails(Model model) {
		BeneficiaryInputDto beneficiaryInputDto=new BeneficiaryInputDto();
		model.addAttribute("beneficiaryInputDto", beneficiaryInputDto);	
		return "beneficiary";
	}
		@RequestMapping("/Details")
		public String saveProfile(@Valid @ModelAttribute BeneficiaryInputDto beneficiaryInputDto,BindingResult result,Model model) {
			if(result.hasErrors()) {
				System.out.println(result);
				return "beneficiary";
			}
			benService.saveDetails(beneficiaryInputDto);
			BeneficiaryOutputDto beneficiaryOutputDto = benService.saveDetails(beneficiaryInputDto);
			if(beneficiaryOutputDto == null) {
				// throw custom exception
				throw new AccountNotFoundException("Account not found with Id : " + beneficiaryInputDto.getId());
			}
			return "beneficiary-details";
		}
		
		@RequestMapping("/modifyDetails")
		public String modifyDetails(Model model) {
			BeneficiaryInputDto beneficiaryInputDto=new BeneficiaryInputDto();
			model.addAttribute("beneficiaryInputDto", beneficiaryInputDto);
			return "modifybeneficiary"; 
			}
		@RequestMapping("/saveDetails")
		public String saveDetails(@Valid @ModelAttribute BeneficiaryInputDto beneficiaryInputDto,BindingResult result,Model model,Long id) {
			if(result.hasErrors()) {
				System.out.println(result);
				return "modifybeneficiary";
			}
			benService.updateDetails(id, beneficiaryInputDto);
			return "beneficiary-details";
		}
		
		@RequestMapping("/loansManagement") 
		public String loansManagement() {
			return"Loan";
		}
		@RequestMapping("/applyLoan") 
		public String applyLoan(Model model) {
			ApplyForLoanInputDto applyForLoanInputDto=new ApplyForLoanInputDto();
			model.addAttribute("applyForLoanInputDto", applyForLoanInputDto);	
			return "Loans_CustomerLogin";
		}
		
		@RequestMapping("/detailsSave") 
		public String detailsSave(@Valid @ModelAttribute ApplyForLoanInputDto applyForLoanInputDto,BindingResult result,Model model) {
			if(result.hasErrors()) {
				System.out.println(result);
				return "Loans_CustomerLogin";
			}
			applyService.saveDetails(applyForLoanInputDto);
			ApplyForLoanOutputDto applyForLoanOutputDto = applyService.saveDetails(applyForLoanInputDto);
			if(applyForLoanOutputDto == null) {
				// throw custom exception
				throw new AccountNotFoundException("Loan Details not found with Id : " + applyForLoanOutputDto.getId());
			}
			return"loanConfirmation";
		}
		@RequestMapping("/payEMI") 
		public String payEMI(Model model) {
			PayEMIInputDto payEMIInputDto=new PayEMIInputDto();
			model.addAttribute("payEMIInputDto", payEMIInputDto);	
			return "payEMI";
		}
		
		@RequestMapping("/payEMISave") 
		public String payEMISave(@Valid @ModelAttribute PayEMIInputDto payEMIInputDto,BindingResult result,Model model) {
			if(result.hasErrors()) {
				System.out.println(result);
				return "payEMI";
			}
			payEMIService.saveDetails(payEMIInputDto);
			return"emiPaymentConfirm";
		}
		
		@RequestMapping("/cardManagement") 
		public String cardManagement() {
			return "cardManagement_CustomerLogin";
		}
		@RequestMapping("/applyCard")
		public String applyCard(Model model) {
			ApplyCardInputDto applyCardInputDto=new ApplyCardInputDto();
			model.addAttribute("applyCardInputDto", applyCardInputDto);
			return "applyForCard";
		}
		@RequestMapping("/saveCard")
		public String saveCard(@ModelAttribute ApplyCardInputDto applyCardInputDto,BindingResult result,Model model) {
			if(result.hasErrors()) {
				System.out.println(result);
				return "applyForCard";
			}
			applyCardService.saveDetails(applyCardInputDto);
			
			return "cardDetailsConfirmation";
		}
		@RequestMapping("/cardUpgrade")
		public String cardUpgrade(Model model) {
			CardUpgradationInputDto cardUpgradationInputDto=new CardUpgradationInputDto();
			model.addAttribute("cardUpgradationInputDto", cardUpgradationInputDto);
			return "cardUpgradation";
		}
		@RequestMapping("/cardUpgradeSave")
		public String cardUpgradeSave(@ModelAttribute CardUpgradationInputDto cardUpgradationInputDto,BindingResult result,Model model) {
			if(result.hasErrors()) {
				System.out.println(result);
				return "cardUpgradation";
			}
			cardUpgradationService.saveDetails(cardUpgradationInputDto);
			CardUpgradationOutputDto cardUpgradationOutputDto = cardUpgradationService.saveDetails(cardUpgradationInputDto);
			if(cardUpgradationOutputDto == null) {
				// throw custom exception
				throw new AccountNotFoundException("Card Details not found with Id : " + cardUpgradationOutputDto.getId());
			}
			return "cardManagement_CustomerLogin";
		}
	  
		@RequestMapping("/resetPIN")
		public String resetPIN(Model model) {
			ResetPinInputDto resetPinInputDto=new ResetPinInputDto();
			model.addAttribute("resetPinInputDto", resetPinInputDto);
			return "resetPin";
		}
		@RequestMapping("/saveResetPIN")
		public String saveResetPIN(@ModelAttribute ResetPinInputDto resetPinInputDto,BindingResult result,Model model) {
			if(result.hasErrors()) {
				System.out.println(result);
				return "resetPin";
			}
			resetPinService.saveDetails(resetPinInputDto);
			return "ibs-home";
		}
}
