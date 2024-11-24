package com.nagarro.bankhub.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nagarro.bankhub.model.Statement;

public interface StatementDao extends JpaRepository<Statement, String> {

	List<Statement> findByAccountId(String id);
}