/**
 * 
 */
package com.forex.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author shar939
 *
 */
@RestController
public class ForexExchangeController {

	@Autowired
	RestTemplate restTemplate;

	@RequestMapping(value = "/forex/from/{from}/to/{to}")
	public BigDecimal getProductList(@PathVariable(value = "from") String from, @PathVariable(value = "to") String to) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		String externalForexURI = "https://free.currconv.com/api/v7/convert?q=" + from + "_" + to
				+ "&compact=ultra&apiKey=2da68da895db5ddfa8b0";

		String exchangeRate = restTemplate.exchange(externalForexURI, HttpMethod.GET, entity, String.class).getBody();
		
		String exchangeValue [] = exchangeRate.split(":");
		
		BigDecimal finalExchangeValue = null;
		if(exchangeValue.length>0) {
			int length = exchangeValue[1].length();
			finalExchangeValue = new BigDecimal(exchangeValue[1].substring(0, length-1));
		}
		
		return finalExchangeValue;
	}
}
