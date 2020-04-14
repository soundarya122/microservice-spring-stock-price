package com.project.stock.dbservice.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.stock.dbservice.model.Quote;
import com.project.stock.dbservice.model.Quotes;
import com.project.stock.dbservice.repo.QuotesRepo;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/rest/db")
public class DbServiceResource {

	@Autowired
	private QuotesRepo quotesRepository;
	
	@GetMapping("/quote/{username}")
	public List<String> getQuotes(@PathVariable String username){
		return getQuotesByUserName(username);
	}
	
	public List<String> getQuotesByUserName(@PathVariable String username){
		List<String> quotes = quotesRepository.findByUserName(username)
			.stream()
			.map(quote -> quote.getQuote())
			.collect(Collectors.toList());
		return quotes;
	}
	
	@GetMapping("/quote")
	public List<Quote> getQuote(){
		return quotesRepository.findAll();
	}
	
	@PostMapping("/quote")
	public List<String> add(@RequestBody final Quotes quotes){
		
		quotes.getQuotes()
			.stream()
			.map(quote->new Quote(quotes.getUserName(),  quote))
			.forEach(quote->quotesRepository.save(quote));
		
		return getQuotesByUserName(quotes.getUserName());
	}
	
	@DeleteMapping("/quote/{userName}")
	public List<String> delete(@PathVariable String userName) {
		List<Quote> quotes = quotesRepository.findByUserName(userName);
		quotesRepository.delete(quotes.get(0));
		return getQuotesByUserName(userName);
	}
	
	@PutMapping("/quote/{userName}")
	public List<String> update(@PathVariable String userName, @RequestBody Quotes quotes) {
		quotes.getQuotes()
		.stream()
		.map(quote->new Quote(quotes.getUserName(),  quote))
		.forEach(quote->quotesRepository.save(quote));
	
		return getQuotesByUserName(quotes.getUserName());
	}
}
