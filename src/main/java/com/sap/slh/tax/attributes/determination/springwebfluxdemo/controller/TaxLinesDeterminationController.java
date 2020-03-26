package com.sap.slh.tax.attributes.determination.springwebfluxdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sap.slh.tax.attributes.determination.springwebfluxdemo.model.RedisRepo;
import com.sap.slh.tax.attributes.determination.springwebfluxdemo.model.TaxDetails;
import com.sap.slh.tax.attributes.determination.springwebfluxdemo.model.TaxLine;
import com.sap.slh.tax.attributes.determination.springwebfluxdemo.service.TaxLineService;
import com.sap.slh.tax.attributes.determination.springwebfluxdemo.util.JsonUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TaxLinesDeterminationController {
	
	private static final Logger log = LoggerFactory.getLogger(TaxLinesDeterminationController.class);
	
	@Autowired
	private TaxLineService taxLineService;
	
	@PostMapping(value = "/tax/lines",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<TaxLine> determineTaxLines(@RequestBody(required = true) final TaxDetails request)
	{
		log.info("the request received is{}",JsonUtil.toJsonString(request));
		return taxLineService.determineTaxLines(request);	
	}
	
	@PostMapping(value = "/tax/lines/save",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<RedisRepo> saveTaxLines(@RequestBody(required = true) final RedisRepo request)
	{
		
		log.info("the request received is{}",JsonUtil.toJsonString(request));
		return taxLineService.saveTaxLines(request);	
	}
	
	@GetMapping(value = "/tax/lines/get")
	public Mono<String> getSimpleString()
	{
		return Mono.just("success");
	}
}
