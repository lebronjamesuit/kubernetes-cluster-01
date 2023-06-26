package com.jamesclear.first.microservice.CurrencyConversionMicroservice.Controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="currency-exchange")   if I want it talks to Eureka

//CHANGE-KUBERNETES
@FeignClient(name = "currency-exchange", url = "${CURRENCY_EXCHANGE_SERVICE_HOST:http://localhost}:8000")
public interface CurrencyConversionProxy {

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	ResponseEntity<CurrencyConversionBean> callCurrencyExchangeService(@PathVariable("from") String from
			,@PathVariable("to") String to);

	
}
