package com.project.stock.dbservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.stock.dbservice.model.Quote;

public interface QuotesRepo extends JpaRepository<Quote, Integer> {

	List<Quote> findByUserName(String quote);
}
