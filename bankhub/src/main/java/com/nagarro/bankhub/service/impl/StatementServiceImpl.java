package com.nagarro.bankhub.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.nagarro.bankhub.dao.AccountDao;
import com.nagarro.bankhub.dao.StatementDao;
import com.nagarro.bankhub.exceptions.DateParsingException;
import com.nagarro.bankhub.exceptions.InValidAccountNumberException;
import com.nagarro.bankhub.model.Account;
import com.nagarro.bankhub.model.Statement;
import com.nagarro.bankhub.responsedto.StatementListResponseDto;
import com.nagarro.bankhub.responsedto.StatementResponseDto;
import com.nagarro.bankhub.service.StatementService;
import com.nagarro.bankhub.utils.Constants;
import com.nagarro.bankhub.utils.DateAndAmounValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * This class is implementation of StatementService
 *
 * @author Rohit Srivastava
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class StatementServiceImpl implements StatementService {

	private final AccountDao accountDao;
	private final StatementDao statementDao;
	private final ModelMapper mapper;

	// Date formatter
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);

	/**
	 *
	 * This method used to get statement of user and admin
	 *
	 * @param accountId
	 * @param fromDate
	 * @param toDate
	 * @param startAmount
	 * @param endAmount
	 * @return
	 */
	@Override
	public StatementListResponseDto statement(String accountId, String fromDate, String toDate, String startAmount,
			String endAmount) {
		// Validate date range
		DateAndAmounValidator.validFromDateToDate(fromDate, toDate);

		// Validate amount range
		DateAndAmounValidator.validFromAmountRange(startAmount, endAmount);

		// Retrieve account details
		Account accountDetails = accountDao.findById(accountId)
				.orElseThrow(() -> new InValidAccountNumberException(Constants.FIELD_ACCOUNT_ID,
						Constants.FILED_ACCOUNT_NUMBER_ERROR));

		// Fetch all statements related to the account
		List<Statement> allStatements = statementDao.findByAccountId(accountDetails.getId());

		// Get authenticated username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		boolean isUser = "user".equals(username);
		String message;

		// Prepare message based on user role and parameters
		if (isUser) {
			if (fromDate != null || toDate != null || startAmount != null || endAmount != null) {
				message = String.format(Constants.MESSAGE_USER_ATTEMPTED_FILTER, fromDate, toDate, startAmount,
						endAmount);
				log.warn(message);
			} else {
				message = Constants.MESSAGE_PAST_3_MONTHS_STATEMENTS;
			}
		} else {
			message = Constants.MESSAGE_STATEMENTS_FETCHED_SUCCESSFULLY;
		}

		// Apply filters
		boolean restrictToLastThreeMonths = isUser
				|| (fromDate == null && toDate == null && startAmount == null && endAmount == null);

		List<Statement> filteredStatements = filterStatements(allStatements, fromDate, toDate, startAmount, endAmount,
				restrictToLastThreeMonths);

		// Convert to StatementResponseDto
		List<StatementResponseDto> statementDtos = filteredStatements.stream()
				.map(statement -> mapper.map(statement, StatementResponseDto.class)).collect(Collectors.toList());

		// Get the masked account number
		String maskedAccountNumber = maskAccountNumber(accountDetails.getAccountNumber());
		// Create and return the response DTO with the account number at the top level
		return new StatementListResponseDto(maskedAccountNumber, statementDtos, message);
	}

	/**
	 * This method return the list of statements
	 * @param statements
	 * @param fromDate
	 * @param toDate
	 * @param startAmount
	 * @param endAmount
	 * @param restrictToLastThreeMonths
	 * @return List<Statement>
	 */
	private List<Statement> filterStatements(List<Statement> statements, String fromDate, String toDate,
											 String startAmount, String endAmount, boolean restrictToLastThreeMonths) {
		LocalDate threeMonthsAgo = getThreeMonthsAgo();

		// Ensure default date range for the last three months
		final LocalDate from = (restrictToLastThreeMonths) ? threeMonthsAgo
				: (fromDate != null ? parseDate(fromDate) : null);
		final LocalDate to = (restrictToLastThreeMonths) ? LocalDate.now()
				: (toDate != null ? parseDate(toDate) : null);

		// If restrictToLastThreeMonths is true (e.g., for user), we skip amount filters
		final Double startAmt = (!restrictToLastThreeMonths && startAmount != null) ? Double.parseDouble(startAmount) : null;
		final Double endAmt = (!restrictToLastThreeMonths && endAmount != null) ? Double.parseDouble(endAmount) : null;

		return statements.stream().filter(statement -> {
			LocalDate statementDate = parseDate(statement.getDatefield());

			// Apply date range filter
			if (from != null && to != null && (statementDate.isBefore(from) || statementDate.isAfter(to))) {
				return false;
			}

			// Apply amount range filter only if restrictToLastThreeMonths is false
			if (startAmt != null && endAmt != null) {
				double amount = Double.parseDouble(statement.getAmount());
				if (amount < startAmt || amount > endAmt) {
					return false;
				}
			}

			return true;
		}).toList();
	}

	/**
	 * This method parse the data from String to Datae format
	 * @param dateStr
	 * @return LocalDate
	 */
	private LocalDate parseDate(String dateStr) {
		try {
			String sanitizedDateStr = dateStr.replace("\"", "").trim();
			return LocalDate.parse(sanitizedDateStr, formatter);
		} catch (Exception e) {
			log.error("Date parsing error: {}", e.getMessage());
			throw new DateParsingException("date", Constants.FILED_DATE_ERROR);
		}
	}

	/**
	 * This method is to get last 3 months data
	 *
	 * @return LocalDate
	 */
	private LocalDate getThreeMonthsAgo() {
		return LocalDate.now().minusMonths(3);
	}

	/**
	 * This method mask the accountNumber
	 * @param accountNumber
	 * @return String
	 */
	private String maskAccountNumber(String accountNumber) {
		int unmaskedDigits = Constants.ACCOUNT_UNMASKED_DIGITS;
		int length = accountNumber.length();
		if (length <= unmaskedDigits) {
			return accountNumber;
		}
		String maskedPart = "*".repeat(length - unmaskedDigits);
		String unmaskedPart = accountNumber.substring(length - unmaskedDigits);
		return maskedPart + unmaskedPart;
	}
}
