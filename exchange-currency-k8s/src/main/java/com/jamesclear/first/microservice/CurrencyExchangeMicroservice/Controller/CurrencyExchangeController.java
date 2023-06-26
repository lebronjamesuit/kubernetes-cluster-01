package com.jamesclear.first.microservice.CurrencyExchangeMicroservice.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jamesclear.first.microservice.CurrencyExchangeMicroservice.Bean.CurrencyExchange;
import com.jamesclear.first.microservice.CurrencyExchangeMicroservice.Service.CurrencyRepo;

@RestController
public class CurrencyExchangeController {

	private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyRepo currencyRepo;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange exchangeCurrency(@PathVariable("from") String from
								  ,@PathVariable("to") String to) {
		
		// both can be useful
		System.out.println(environment.getProperty("local.server.port"));
		logger.info("Log at CurrencyExchangeController.class, path exchangeCurrency");

		CurrencyExchange currencyExchange = currencyRepo.findByFromAndTo(from, to);
		if(currencyExchange == null) {
			throw new RuntimeException("no found");
		}

		String port = environment.getProperty("local.server.port");

		//CHANGE-KUBERNETES
		String host = environment.getProperty("HOSTNAME");
		String version = "v12";

		currencyExchange.setEnvironment(port + " " + version + " " + host);


		return currencyExchange;
	}
}
