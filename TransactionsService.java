package com.wf.ibs.bootappsecure.service;

import java.text.ParseException;
import java.util.List;

import com.wf.ibs.bootappsecure.dto.BillPaymentsInputDto;
import com.wf.ibs.bootappsecure.dto.BillPaymentsOutputDto;
import com.wf.ibs.bootappsecure.dto.FundsTransferInputDto;
import com.wf.ibs.bootappsecure.dto.FundsTransferOutputDto;
import com.wf.ibs.bootappsecure.dto.StatementInputDto;
import com.wf.ibs.bootappsecure.dto.StatementOutputDto;

public interface TransactionsService {

	public BillPaymentsOutputDto saveBillTxnDetails(BillPaymentsInputDto billInputDto) throws ParseException;
	public FundsTransferOutputDto saveFundTxnDetails(FundsTransferInputDto fundInputDto) throws ParseException;
	public List<StatementOutputDto> fetchStatement(StatementInputDto stmtInputDto) throws ParseException;
}
