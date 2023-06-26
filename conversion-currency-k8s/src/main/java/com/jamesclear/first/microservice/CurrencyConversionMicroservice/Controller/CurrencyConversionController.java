package com.jamesclear.first.microservice.CurrencyConversionMicroservice.Controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class CurrencyConversionController {
	
	private Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);
	
	@Autowired
	private CurrencyConversionProxy currencyFeignProxy;
	
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quanlity/{quanlity}")
	public ResponseEntity<CurrencyConversionBean> getCurrent(@PathVariable("from") String from
			,@PathVariable("to") String to
			,@PathVariable("quanlity") String quanlity) {
		
		System.out.println("restTemplate " + "/currency-conversion/from/{from}/to/{to}/quanlity/{quanlity}");
		logger.info(" Log at CurrencyConversionController.class method getCurrent rest template ", this);

		String url = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";
		HashMap<String, String> variables = new HashMap<String, String>();
		variables.put("from", from);
		variables.put("to", to);
	
		ResponseEntity<CurrencyConversionBean> responseConversion = restTemplate.getForEntity(url,CurrencyConversionBean.class, variables);
		CurrencyConversionBean bean  = responseConversion.getBody();
		bean.setTotalCalculatedAmount(bean.getConversionMultiple() * Double.valueOf(quanlity));
		bean.setQuantity(Integer.valueOf(quanlity));
		bean.setEnvironment(bean.getEnvironment()+ "  REST TEMPLATE");
		return responseConversion;
	}
	
	@GetMapping("/feign/currency-conversion/from/{from}/to/{to}/quanlity/{quanlity}")
	public ResponseEntity<CurrencyConversionBean> currencyConverse(@PathVariable("from") String from
			,@PathVariable("to") String to
			,@PathVariable("quanlity") String quanlity) {

		//CHANGE-K8S
		logger.info(" Feign - CurrencyConversionController.class method: currencyConverse called with {} to {} with {}", from, to);
		ResponseEntity<CurrencyConversionBean> responseConversion =  null;
		responseConversion = currencyFeignProxy.callCurrencyExchangeService(from, to);
		CurrencyConversionBean bean  = responseConversion.getBody();
		bean.setTotalCalculatedAmount(bean.getConversionMultiple() * Double.valueOf(quanlity));
		bean.setQuantity(Integer.valueOf(quanlity));
		bean.setEnvironment(bean.getEnvironment()+ "  FEIGN");
		return responseConversion;
	}
	
}

