package com.sap.slh.tax.attributes.determination.springwebfluxdemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.slh.tax.attributes.determination.springwebfluxdemo.lookup.RedisTaxLineLookUpService;
import com.sap.slh.tax.attributes.determination.springwebfluxdemo.model.RedisRepo;
import com.sap.slh.tax.attributes.determination.springwebfluxdemo.model.TaxDetails;
import com.sap.slh.tax.attributes.determination.springwebfluxdemo.model.TaxLine;
import com.sap.slh.tax.attributes.determination.springwebfluxdemo.util.JsonUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaxLineService {
	private static final Logger log = LoggerFactory.getLogger(TaxLineService.class);
	
	@Autowired
	private RedisTaxLineLookUpService redisTaxLineLookUpService;
	
	public Flux<TaxLine> determineTaxLines(TaxDetails taxDetails)
	{
		log.info("determining tax lines for{}",JsonUtil.toJsonString(taxDetails));
		return redisTaxLineLookUpService.get(taxDetails);	

	}

	public Mono<RedisRepo> saveTaxLines(RedisRepo redisRepo) {
		log.info("saving tax lines for{}",JsonUtil.toJsonString(redisRepo));
		return redisTaxLineLookUpService.set(redisRepo);
	}

}
