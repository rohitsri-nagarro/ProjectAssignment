package com.nagarro.bankhub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nagarro.bankhub.responsedto.StatementListResponseDto;
import com.nagarro.bankhub.service.StatementService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/view")
public class StatementController {

	private final StatementService statementService;

	@GetMapping("statement/{accountId}")
	public ResponseEntity<StatementListResponseDto> viewStatement(@PathVariable String accountId,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate,
			@RequestParam(required = false) String amountStart, @RequestParam(required = false) String amountEnd) {

		StatementListResponseDto statementListResponseDto = statementService.statement(accountId, startDate, endDate,
				amountStart, amountEnd);

		// Return a typed ResponseEntity
		return new ResponseEntity<StatementListResponseDto>(statementListResponseDto, HttpStatus.OK);
	}
}