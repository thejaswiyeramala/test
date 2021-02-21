package com.wf.ibs.bootappsecure.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wf.ibs.bootappsecure.dto.BillPaymentsInputDto;
import com.wf.ibs.bootappsecure.dto.BillPaymentsOutputDto;
import com.wf.ibs.bootappsecure.dto.FundsTransferInputDto;
import com.wf.ibs.bootappsecure.dto.FundsTransferOutputDto;
import com.wf.ibs.bootappsecure.dto.StatementInputDto;
import com.wf.ibs.bootappsecure.dto.StatementOutputDto;
import com.wf.ibs.bootappsecure.entity.Accounts;
import com.wf.ibs.bootappsecure.entity.Transactions;
import com.wf.ibs.bootappsecure.repository.AccountsRepository;
import com.wf.ibs.bootappsecure.repository.TransactionsRepository;
import com.wf.ibs.bootappsecure.service.TransactionsService;

@Service
public class TransactionsServiceImpl implements TransactionsService{

	//injecting a dependency
	@Autowired
	private TransactionsRepository txnRepository;
	@Autowired
	private AccountsRepository acctsRepository;
	
	@Override
	public BillPaymentsOutputDto saveBillTxnDetails(BillPaymentsInputDto billInputDto) throws ParseException {
	
		if(this.acctsRepository.existsById(billInputDto.getAcctNum())) {
			//verify balance
			Accounts accts = this.acctsRepository.findById(billInputDto.getAcctNum()).orElse(null);
			
			if(billInputDto.getBillAmount() <= accts.getBalance()) {
				//set acct id in entity
				Accounts acct = new Accounts();
				acct.setActId(accts.getActId());
				acct.setAccountType(accts.getAccountType());
				acct.setDateCreated(accts.getDateCreated());
				acct.setStatus(accts.getStatus());
				acct.setCustomer(accts.getCustomer());
				//deduct the amount transferred from the balance
				Double closingbalance = accts.getBalance()-billInputDto.getBillAmount();
				acct.setBalance(closingbalance);
				// save into DB, returns newly added record
				@SuppressWarnings("unused")
				Accounts newAccts = this.acctsRepository.save(acct);
								
				// convert dto into entity
				Transactions txns = this.convertTxnInputDtoToEntity(billInputDto);
				//join  acct id to transactions table
				txns.setAccounts(acct);
						
				// save into DB, returns newly added record
				Transactions newTxn = this.txnRepository.save(txns);
				// convert entity into dto
				BillPaymentsOutputDto billOutputDto = this.convertTxnEntityToOutputDto(newTxn);
				billOutputDto.setClosingBalance(closingbalance);
				return billOutputDto;
			}
			else
				return null;
		}
		return null;
	}
	
	@Override
	public FundsTransferOutputDto saveFundTxnDetails(FundsTransferInputDto fundInputDto) throws ParseException {
	
		if(this.acctsRepository.existsById(fundInputDto.getAcctNum())) {
			//verify balance
			Accounts accts = this.acctsRepository.findById(fundInputDto.getAcctNum()).orElse(null);
			
			if(fundInputDto.getTransferAmount() <= accts.getBalance()) {
				//set acct id in entity
				Accounts acct = new Accounts();
				acct.setActId(accts.getActId());
				acct.setAccountType(accts.getAccountType());
				acct.setDateCreated(accts.getDateCreated());
				acct.setStatus(accts.getStatus());
				acct.setCustomer(accts.getCustomer());
				//deduct the amount transferred from the balance
				Double closingbalance = accts.getBalance()-fundInputDto.getTransferAmount();
				acct.setBalance(closingbalance);
				// save into DB, returns newly added record
				@SuppressWarnings("unused")
				Accounts newAccts = this.acctsRepository.save(acct);
								
				// convert dto into entity
				Transactions txns = this.convertFundTxnInputDtoToEntity(fundInputDto);
				//join  acct id to transactions table
				txns.setAccounts(acct);
				// save into DB, returns newly added record
				Transactions newTxn = this.txnRepository.save(txns);
				// convert entity into dto
				FundsTransferOutputDto fundOutputDto = this.convertFundTxnEntityToOutputDto(newTxn);
				fundOutputDto.setClosingBalance(closingbalance);
				return fundOutputDto;
			}
		}
		return null;
	}
	
	@Override
	public List<StatementOutputDto> fetchStatement(StatementInputDto stmtInputDto) throws ParseException {
		
		Accounts acct = new Accounts();
		acct.setActId(stmtInputDto.getAcctNum());
		List<Transactions> txns = this.txnRepository.findAllByAccountID(acct);
		
		List<StatementOutputDto> stmtOutputDtos = new ArrayList<StatementOutputDto>();
		
		//format startdate mm/dd/yyyy calendar object to Date format
		String[] startDate = stmtInputDto.getStartDate().split("/");
		int strYear = Integer.parseInt(startDate[2]);
		int strMonth = Integer.parseInt(startDate[0])-1;
		int strDate = Integer.parseInt(startDate[1]);
		Calendar calStart = Calendar.getInstance();
		calStart.set(strYear, strMonth, strDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strStartDate = dateFormat.format(calStart.getTime());
		Date dateStart= dateFormat.parse(strStartDate);
		
						
		//format enddate mm/dd/yyyy calendar object to Date format
		String[] endDate = stmtInputDto.getEndDate().split("/");
		int endYear = Integer.parseInt(endDate[2]);
		int endMonth = Integer.parseInt(endDate[0])-1;
		int endDates = Integer.parseInt(endDate[1]);
		Calendar calEnd = Calendar.getInstance();
		calEnd.set(endYear, endMonth, endDates);
		String strEndDate = dateFormat.format(calEnd.getTime());
		Date dateEnd= dateFormat.parse(strEndDate);
		
		for(Transactions txn: txns) {
			
			if((dateStart.before(txn.getTxnDate())) && (dateEnd).after(txn.getTxnDate())) {
				StatementOutputDto stmtOutputDto = this.convertTxnEntityToStmtOutputDto(txn);
				stmtOutputDtos.add(stmtOutputDto);
			}
			
		}
		
		return stmtOutputDtos;
		
	}
	
	//utility method
	private Transactions convertTxnInputDtoToEntity(BillPaymentsInputDto billInputDto) throws ParseException {
		Transactions txns = new Transactions();
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(currentDate);
		Date txnDate= dateFormat.parse(strDate);
		
		txns.setBillType(billInputDto.getBillType());
		//format selected serv prov to string
		String str = billInputDto.getServProvider().toString();
		String temp = str.replaceAll("[\\[\\]]", "");
		
		txns.setServProv(temp);
		txns.setBillAmount(billInputDto.getBillAmount());
		txns.setDescription(billInputDto.getBillDescription());
		txns.setTxnDate(txnDate);
		txns.setStatus("Success");
		return txns;
	}
	
	// utility method
	private BillPaymentsOutputDto convertTxnEntityToOutputDto(Transactions txn) {
		BillPaymentsOutputDto billOutputDto = new BillPaymentsOutputDto();
		
		billOutputDto.setTxnId(txn.getTxnId());
		billOutputDto.setAcctNum(txn.getAccounts().getActId());
		billOutputDto.setBillType(txn.getBillType());
		billOutputDto.setServProvider(txn.getServProv());
		billOutputDto.setBillAmount(txn.getBillAmount());
		billOutputDto.setBillDescription(txn.getDescription());
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		billOutputDto.setTxnDate(dateFormat.format(txn.getTxnDate()));
		billOutputDto.setStatus(txn.getStatus());
		
		return billOutputDto;
	}

	

	//utility method
	private Transactions convertFundTxnInputDtoToEntity(FundsTransferInputDto fundInputDto) throws ParseException {
		Transactions txns = new Transactions();
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(currentDate);
		Date txnDate= dateFormat.parse(strDate);
				
		//format selected Beneficiary to string
		String str = fundInputDto.getBeneficiary().toString();
		String temp = str.replaceAll("[\\[\\]]", "");
		
		txns.setBeneficiary(temp);
		txns.setBillAmount(fundInputDto.getTransferAmount());
		txns.setBillType(fundInputDto.getBillType());
		txns.setDescription(fundInputDto.getTransferDescription());
		txns.setTxnDate(txnDate);
		txns.setStatus("Success");
		return txns;
	}
	
	// utility method
	private FundsTransferOutputDto convertFundTxnEntityToOutputDto(Transactions txn) {
		FundsTransferOutputDto fundOutputDto = new FundsTransferOutputDto();
		
		fundOutputDto.setTxnId(txn.getTxnId());
		fundOutputDto.setAcctNum(txn.getAccounts().getActId());
		fundOutputDto.setBillType(txn.getBillType());
		fundOutputDto.setBeneficiary(txn.getBeneficiary());
		fundOutputDto.setTransferAmount(txn.getBillAmount());
		fundOutputDto.setTransferDescription(txn.getDescription());
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		fundOutputDto.setTxnDate(dateFormat.format(txn.getTxnDate()));
		fundOutputDto.setStatus(txn.getStatus());
		
		return fundOutputDto;
	}
	
	// utility method
	private StatementOutputDto convertTxnEntityToStmtOutputDto(Transactions txn) {
		StatementOutputDto stmtOutputDto = new StatementOutputDto();
		
		stmtOutputDto.setTxnId(txn.getTxnId());
		stmtOutputDto.setAcctId(txn.getAccounts().getActId());
		stmtOutputDto.setBillAmount(txn.getBillAmount());
		stmtOutputDto.setBillType(txn.getBillType());
		stmtOutputDto.setDescription(txn.getDescription());
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		String strTxnDate = dateFormat.format(txn.getTxnDate());
		stmtOutputDto.setTxnDate(strTxnDate);
			if(txn.getBeneficiary()!=null)
				stmtOutputDto.setBeneficiary(txn.getBeneficiary());
			else
				stmtOutputDto.setBeneficiary("N/A");
			
			if(txn.getServProv()!=null)
				stmtOutputDto.setServProvider(txn.getServProv());
			else
				stmtOutputDto.setServProvider("N/A");
		
		stmtOutputDto.setStatus(txn.getStatus());
		
		return stmtOutputDto;
	}
	
}
