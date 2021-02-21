package com.wf.ibs.bootappsecure.service;

import java.text.ParseException;

import com.wf.ibs.bootappsecure.dto.AccountsInputDto;
import com.wf.ibs.bootappsecure.dto.AccountsOutputDto;
import com.wf.ibs.bootappsecure.dto.BalanceOutputDto;

public interface AccountsService {

	public AccountsOutputDto saveAcctDetails(AccountsInputDto acctsInputDto) throws ParseException;
	public BalanceOutputDto fetchAcctBalance(Long acctNum);
}
