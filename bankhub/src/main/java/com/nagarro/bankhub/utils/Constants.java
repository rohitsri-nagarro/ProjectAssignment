package com.nagarro.bankhub.utils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Constants {
	
	// Field Names
	public static final String FIELD_ACCOUNT_NUMBER = "Account Number";
	public static final String FIELD_ACCOUNT_ID = "Account Id";
	public static final String FIELD_FROM_DATE = "fromDate";
	public static final String FIELD_TO_DATE = "toDate";
	public static final String FIELD_AMOUNT_START = "amountStart";
	public static final String FIELD_AMOUNT_END = "amountEnd";

	// General Errors
	public static final String ERROR_INVALID_DATE_RANGE = "Invalid date range: '%s' cannot be after '%s'.";
	public static final String ERROR_INVALID_DATE_FORMAT = "Invalid date format for '%s'. Expected format is dd.MM.yyyy.";
	public static final String ERROR_MISSING_DATE = "%s is null or empty while %s is provided.";
	public static final String ERROR_MISSING_AMOUNT = "%s is null or empty while %s is provided.";
	public static final String ERROR_AMOUNT_RANGE = "amountEnd '%s' cannot be less than amountStart '%s'.";
	public static final String ERROR_INVALID_AMOUNT_FORMAT = "Invalid amount format for '%s'. Expected numeric values.";
	public static final String FILED_AMOUNT_ERROR = "Invalid amount format.";
    public static final String FILED_DATE_ERROR = "Invalid date format.";
    public static final String FILED_DATE_RANGE_ERROR = "Start date cannot be after end date.";

	// Account Errors
	public static final String FILED_ACCOUNT_NUMBER_ERROR = "Invalid Account Number";
	public static final String FILED_ACCOUNT = "Amount Field";
	public static final String FILED_ACCOUNT_ERROR = "Invalid Amount format";

	// Date Errors
	public static final String FILED_DATE_FIELD = "Date Field";
	public static final String FILED_DATE_FIELD_ERROR = "Date field format is not correct";

	// Messages
	public static final String MESSAGE_STATEMENTS_FETCHED_SUCCESSFULLY = "Statements fetched successfully.";
	public static final String MESSAGE_PAST_3_MONTHS_STATEMENTS = "Past 3 months' statements fetched successfully.";
	public static final String MESSAGE_USER_ATTEMPTED_FILTER = "User attempted to filter statements with parameters: fromDate=%s, toDate=%s, startAmount=%s, endAmount=%s. "
			+ "Returning past 3 months' statements only.";

	// Date format
	public static final String DATE_FORMAT = "dd.MM.yyyy";

	// Masking Constants
	public static final int ACCOUNT_UNMASKED_DIGITS = 4;
}
