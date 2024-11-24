package com.nagarro.bankhub.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.nagarro.bankhub.exceptions.InValidAmountException;
import com.nagarro.bankhub.exceptions.InValidDateException;

public class DateAndAmounValidator {

	private static final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

	/**
	 * Validates the date range between fromDate and toDate.
	 *
	 * @param fromDate The start date in dd.MM.yyyy format.
	 * @param toDate   The end date in dd.MM.yyyy format.
	 * @return true if the date range is valid.
	 * @throws InValidDateException if any validation fails.
	 */
	public static boolean validFromDateToDate(String fromDate, String toDate) {
		// Check if both dates are null or empty
		if (isNullOrEmpty(fromDate) && isNullOrEmpty(toDate)) {
			return true; // No dates provided is valid
		}

		// Check if only one date is null or empty
		if (isNullOrEmpty(fromDate)) {
			throw new InValidDateException(Constants.FIELD_FROM_DATE,
					String.format(Constants.ERROR_MISSING_DATE, Constants.FIELD_FROM_DATE, Constants.FIELD_TO_DATE));
		}
		if (isNullOrEmpty(toDate)) {
			throw new InValidDateException(Constants.FIELD_TO_DATE,
					String.format(Constants.ERROR_MISSING_DATE, Constants.FIELD_TO_DATE, Constants.FIELD_FROM_DATE));
		}

		// Validate the date range
		try {
			var start = formatter.parse(fromDate);
			var end = formatter.parse(toDate);

			if (start.after(end)) {
				throw new InValidDateException(Constants.FIELD_FROM_DATE,
						String.format(Constants.ERROR_INVALID_DATE_RANGE, fromDate, toDate));
			}
		} catch (ParseException e) {
			throw new InValidDateException(Constants.FILED_DATE_FIELD, Constants.ERROR_INVALID_DATE_FORMAT);
		}

		return true;
	}

	/**
	 * Validates the amount range between startAmount and endAmount.
	 *
	 * @param amountStart The starting amount as a string.
	 * @param amountEnd   The ending amount as a string.
	 * @return true if the amount range is valid.
	 * @throws InValidDateException if any validation fails.
	 */
	public static boolean validFromAmountRange(String amountStart, String amountEnd) {
		// Check if both amounts are null or empty
		if (isNullOrEmpty(amountStart) && isNullOrEmpty(amountEnd)) {
			return true; // No amounts provided is valid
		}

		// Check if only one amount is null or empty
		if (isNullOrEmpty(amountStart)) {
			throw new InValidAmountException(Constants.FIELD_AMOUNT_START, String.format(Constants.ERROR_MISSING_AMOUNT,
					Constants.FIELD_AMOUNT_START, Constants.FIELD_AMOUNT_END));
		}
		if (isNullOrEmpty(amountEnd)) {
			throw new InValidAmountException(Constants.FIELD_AMOUNT_END, String.format(Constants.ERROR_MISSING_AMOUNT,
					Constants.FIELD_AMOUNT_END, Constants.FIELD_AMOUNT_START));
		}

		// Validate the amount range
		try {
			double start = Double.parseDouble(amountStart);
			double end = Double.parseDouble(amountEnd);

			if (end < start) {
				throw new InValidAmountException(Constants.FIELD_AMOUNT_START,
						String.format(Constants.ERROR_AMOUNT_RANGE, amountEnd, amountStart));
			}
		} catch (NumberFormatException e) {
			throw new InValidAmountException(Constants.FILED_ACCOUNT, Constants.ERROR_INVALID_AMOUNT_FORMAT);
		}

		return true;
	}

	/**
	 * Helper method to check if a string is null or empty.
	 *
	 * @param str The string to check.
	 * @return true if the string is null or empty, false otherwise.
	 */
	private static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
}
