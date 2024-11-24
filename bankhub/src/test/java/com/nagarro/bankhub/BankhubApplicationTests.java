package com.nagarro.bankhub;

import com.nagarro.bankhub.dao.AccountDao;
import com.nagarro.bankhub.dao.StatementDao;
import com.nagarro.bankhub.exceptions.InValidAmountException;
import com.nagarro.bankhub.exceptions.InValidDateException;
import com.nagarro.bankhub.model.Account;
import com.nagarro.bankhub.model.Statement;
import com.nagarro.bankhub.responsedto.StatementListResponseDto;
import com.nagarro.bankhub.service.impl.StatementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class BankhubApplicationTests {

	@InjectMocks
	private StatementServiceImpl statementService;

	@Mock
	private AccountDao accountDao;

	@Mock
	private StatementDao statementDao;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private SecurityContext securityContext;

	@Mock
	private Authentication authentication;

	@BeforeEach
	public void setup() {
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);
	}


	@Test
	void shouldReturnStatementsForAdminWithFilters()  {
		String accountNumber = "123456";
		String fromDate = "01.01.2023";
		String toDate = "01.03.2023";
		String startAmount = "100";
		String endAmount = "500";

		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setId("5");
		Optional<Account> optionalAccount = Optional.of(account);
		when(accountDao.findById(account.getId())).thenReturn(optionalAccount);

		List<Statement> statements = List.of(
				createStatement("10.01.2023", "150"),
				createStatement("15.02.2023", "200"),
				createStatement("25.03.2023", "600")
		);
		when(statementDao.findByAccountId(account.getId())).thenReturn(statements);
		when(authentication.getName()).thenReturn("admin");
		// Act
		StatementListResponseDto result = statementService.statement(account.getId(),fromDate, toDate, startAmount, endAmount);
		assertNotNull(result);
		assertEquals(2, result.getStatememts().size());
	}

	@Test
	void shouldReturnStatementsForUserWithFilters()  {
		String accountNumber = "123456";
		String fromDate = "01.01.2023";
		String toDate = "01.03.2023";
		String startAmount = "100";
		String endAmount = "500";

		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setId("5");
		Optional<Account> optionalAccount = Optional.of(account);
		when(accountDao.findById(account.getId())).thenReturn(optionalAccount);

		List<Statement> statements = List.of(
				createStatement("10.01.2024", "150"),
				createStatement("15.02.2024", "200"),
				createStatement("25.03.2024", "600")
		);
		when(statementDao.findByAccountId(account.getId())).thenReturn(statements);
		when(authentication.getName()).thenReturn("user");
		// Act
		StatementListResponseDto result = statementService.statement(account.getId(),fromDate, toDate, startAmount, endAmount);
		assertNotNull(result);
	}

	@Test
	void shouldThrowExceptionForInvalidAmountStartAmount() {
		String fromDate = "01.01.2023";
		String toDate = "01.03.2023";
		when(authentication.getName()).thenReturn("admin");
		assertThrows(InValidAmountException.class,
				() -> statementService.statement("5", fromDate, toDate, "100", null));

	}

	@Test
	void shouldThrowExceptionForInvalidAmountEndAmount() {
		String fromDate = "01.01.2023";
		String toDate = "01.03.2023";
		when(authentication.getName()).thenReturn("admin");
		assertThrows(InValidAmountException.class,
				() -> statementService.statement("5", fromDate, toDate, null, "100"));

	}

	@Test
	void shouldThrowExceptionForInvalidDateStart() {
		String fromDate = "01.01.2023";
		when(authentication.getName()).thenReturn("admin");
		assertThrows(InValidDateException.class,
				() -> statementService.statement("5", fromDate, null, "10", "1000"));

	}

	@Test
	void shouldThrowExceptionForInvalidAmountRangeMismatch() {
		when(authentication.getName()).thenReturn("admin");
		assertThrows(InValidAmountException.class,
				() -> statementService.statement("5", null, null, "1000", "10"));
	}


	@Test
	void shouldThrowExceptionForInvalidDateEnd() {
		String toDate = "01.03.2023";
		when(authentication.getName()).thenReturn("admin");
		assertThrows(InValidDateException.class,
				() -> statementService.statement("5", null, toDate, "10", "1000"));

	}

	@Test
	void shouldThrowExceptionForInvalidStartAndEndMistach() {
		String fromDate = "01.01.2020";
		String toDate = "01.03.2015";
		when(authentication.getName()).thenReturn("admin");
		assertThrows(InValidDateException.class,
				() -> statementService.statement("5", fromDate, toDate, "10", "1000"));

	}


	private Statement createStatement(String date, String amount){
		Statement statement = new Statement();
		statement.setDatefield(date);
		statement.setAmount(amount);
		return statement;
	}

	@Test
	void contextLoads() {
	}

}
