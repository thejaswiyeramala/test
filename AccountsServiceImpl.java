package com.wf.ibs.bootappsecure.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wf.ibs.bootappsecure.dto.AccountsInputDto;
import com.wf.ibs.bootappsecure.dto.AccountsOutputDto;
import com.wf.ibs.bootappsecure.dto.BalanceOutputDto;
import com.wf.ibs.bootappsecure.entity.Accounts;
import com.wf.ibs.bootappsecure.entity.Customer;
import com.wf.ibs.bootappsecure.repository.AccountsRepository;
import com.wf.ibs.bootappsecure.repository.CustomerRepository;
import com.wf.ibs.bootappsecure.service.AccountsService;

@Service
public class AccountsServiceImpl implements AccountsService{

	//injecting a dependency
	@Autowired
	private CustomerRepository custRepository;
	@Autowired
	private AccountsRepository acctsRepository;
	
	
	//utility method
	private Accounts convertInputDtoToEntity(AccountsInputDto acctsInputDto) throws ParseException {
		Accounts accts = new Accounts();
		Date currentDate = new Date();
		//SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(currentDate);
		Date dateCreated= dateFormat.parse(strDate);
		
		//accts.setUci(acctsInputDto.getUci());
		accts.setAccountType(acctsInputDto.getAccountType());
		accts.setDateCreated(dateCreated);
		accts.setStatus("Active");
		return accts;
	}
	
	// utility method
	private AccountsOutputDto convertEntityToOutputDto(Accounts accts) {
		AccountsOutputDto acctsOutputDto = new AccountsOutputDto();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		
		acctsOutputDto.setUci(accts.getCustomer().getUci());
		acctsOutputDto.setAccountType(accts.getAccountType());
		acctsOutputDto.setAccountNum(accts.getActId());
		//acctsOutputDto.setBalance(accts.getBalance());
		acctsOutputDto.setDateCreated(dateFormat.format(accts.getDateCreated()));
		acctsOutputDto.setStatus(accts.getStatus());
		
		return acctsOutputDto;
	}
	
	private BalanceOutputDto convertBalanceEntityToOutputDto(Accounts accts) {
		BalanceOutputDto balOutputDto = new BalanceOutputDto();
		balOutputDto.setAccountNum(accts.getActId());
		balOutputDto.setAccountType(accts.getAccountType());
		balOutputDto.setBalance(accts.getBalance());
		return balOutputDto;
	}
		
	@Override
	public AccountsOutputDto saveAcctDetails(AccountsInputDto acctsInputDto) throws ParseException {
		if(this.custRepository.existsById(acctsInputDto.getUci())) {
		//set customer id dto in entity
		Customer cust = new Customer();
		cust.setUci(acctsInputDto.getUci());
			
		// convert dto into entity
		Accounts accts = this.convertInputDtoToEntity(acctsInputDto);
		//join set customer id to accounts table
		accts.setCustomer(cust);
		// save into DB, returns newly added record
		Accounts newAccts = this.acctsRepository.save(accts);
		// convert entity into dto
		AccountsOutputDto acctsOutputDto = this.convertEntityToOutputDto(newAccts);
		return acctsOutputDto;
		}
		return null;
	}

	@Override
	public BalanceOutputDto fetchAcctBalance(Long acctNum) {
		if(this.acctsRepository.existsById(acctNum)) {
			Accounts accts = this.acctsRepository.findById(acctNum).orElse(null);
			BalanceOutputDto balOutputDto = this.convertBalanceEntityToOutputDto(accts);
			return balOutputDto;
		}
		return null;
	}

}
