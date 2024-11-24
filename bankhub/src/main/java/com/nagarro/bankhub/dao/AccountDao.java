package com.nagarro.bankhub.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nagarro.bankhub.model.Account;

public interface AccountDao extends JpaRepository<Account, String> {
	Optional<Account> findByAccountNumber(String accountNumber);

}
