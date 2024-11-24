package com.nagarro.bankhub.service;

import com.nagarro.bankhub.responsedto.StatementListResponseDto;

public interface StatementService {

	StatementListResponseDto statement(String accountId, String fromDate, String toDate, String startAmount, String endAmount);

}
