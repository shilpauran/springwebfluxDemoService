//package com.sap.slh.tax.attributes.determination.springwebfluxdemo.controller;
//
//
//import java.util.concurrent.ExecutionException;
//
//import javax.validation.Valid;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.AmqpRemoteException;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageProperties;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
//import org.springframework.amqp.rabbit.AsyncRabbitTemplate.RabbitMessageFuture;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.sap.slh.tax.attributes.determination.springwebfluxdemo.model.TaxDetails;
//import com.sap.slh.tax.attributes.determination.springwebfluxdemo.model.TaxLine;
//import com.sap.slh.tax.attributes.determination.springwebfluxdemo.util.JsonUtil;
//
//import reactor.core.publisher.Flux;
//
//@Configuration
//@Controller
//@RequestMapping("/send/message")
//public class TaxDeterminationController {
//	private static final Logger log = LoggerFactory.getLogger(TaxDeterminationController.class);
//
//	@Autowired
//	private AsyncRabbitTemplate template;
//
//	@RequestMapping(method = RequestMethod.POST)
//	public @ResponseBody Flux<TaxLine> onRootAccess(
//			@Valid @RequestBody(required = true) final TaxDetails request) {
//		
//
//		TopicExchange exchange = new TopicExchange("tax.webflux.reactor.TAXSERVICE");
////		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
////		AsyncRabbitTemplate template = new AsyncRabbitTemplate(rabbitTemplate);
//		MessageProperties messageProperties = new MessageProperties();
////		messageProperties.setContentType("application/json");
////		messageProperties.setHeader("Accept-Language", "en");
////		messageProperties.setHeader(HttpHeaders.AUTHORIZATION,
////				"Bearer eyJhbGciOiJSUzI1NiIsImprdSI6Imh0dHBzOi8vdGFhc2dzLmF1dGhlbnRpY2F0aW9uLnNhcC5oYW5hLm9uZGVtYW5kLmNvbS90b2tlbl9rZXlzIiwia2lkIjoia2V5LWlkLTEiLCJ0eXAiOiJKV1QifQ.eyJqdGkiOiIwNDhiN2U3Yjk3MjE0N2I2YWUxZDIwZjE4MzkwOTFiZSIsImV4dF9hdHRyIjp7ImVuaGFuY2VyIjoiWFNVQUEiLCJ6ZG4iOiJ0YWFzZ3MifSwic3ViIjoic2ItdGF4LXNlcnZpY2UteHN1YWEhYjU1MjAiLCJhdXRob3JpdGllcyI6WyJ1YWEucmVzb3VyY2UiXSwic2NvcGUiOlsidWFhLnJlc291cmNlIl0sImNsaWVudF9pZCI6InNiLXRheC1zZXJ2aWNlLXhzdWFhIWI1NTIwIiwiY2lkIjoic2ItdGF4LXNlcnZpY2UteHN1YWEhYjU1MjAiLCJhenAiOiJzYi10YXgtc2VydmljZS14c3VhYSFiNTUyMCIsImdyYW50X3R5cGUiOiJjbGllbnRfY3JlZGVudGlhbHMiLCJyZXZfc2lnIjoiZjAyZGViMTAiLCJpYXQiOjE1Nzk4NDYwNDIsImV4cCI6MTU3OTg4OTI0MiwiaXNzIjoiaHR0cDovL3RhYXNncy5sb2NhbGhvc3Q6ODA4MC91YWEvb2F1dGgvdG9rZW4iLCJ6aWQiOiJmMmU4YmYwNC1hOTUyLTQzOTQtYTY3YS1hZjIyY2RiOWI1MjQiLCJhdWQiOlsidWFhIiwic2ItdGF4LXNlcnZpY2UteHN1YWEhYjU1MjAiXX0.lRkimipII0crfq4dDKOqRZdxBK14Gn2GBtofmqaupyVbgCipDmFZbBeaxgeg6US5tzGABFaXlDeNb05esYLZLu_7vC0jTx_JNW1W-tLC-PhQqJWg6nvw5QRepnjWMPSW_rjGNJHOgsJri_CvoxbfVsQO8toUzRtJm-IoPNSOiS5LDKGxNk9KpQ0vsOJhX2sqzLPr23V7WIZS8dQ5KxwPRonGbpFVMb7TBWqpSAr0hBNp4lQmqbKExf84gNQb4IBNS2iLA-pKqeq_OAZ76F5CZRm1f-P81SfFSre5lcsDzeItTxaLMUeeFae6qDK-oT2_35TzKc4zrtvtpvSsd1waYA");
////		
//		log.error("Tax determination request {}", JsonUtil.toJsonString(request));
//		Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
//
//		Message sentMsg = converter.toMessage(request, messageProperties);
//
//		log.info("Sent Message{}", sentMsg);
//		RabbitMessageFuture receivedFuture = null;
//		try {
//			receivedFuture = template.sendAndReceive(exchange.getName(),
//					"tax.webflux.reactor.determine", sentMsg);
//			log.info("Received message: {}", receivedFuture);
//		} catch (AmqpRemoteException e) {
//			log.error("Exception occured in processing", e);
//		}
//		Message receivedMessage = null;
//		try {
//			receivedMessage = receivedFuture.get();
//		} catch (InterruptedException | ExecutionException e) {
//			log.error("received exeption while converting to message{}",e);
//		}
//		
//		TaxLine obj = (TaxLine) converter.fromMessage(receivedMessage);
//		log.info("Received object: {}", obj);
////		TaxAttributesDeterminationResponseModel model = new TaxAttributesDeterminationResponseModel();
////		model.getStatus();
//		return Flux.just(obj);
//
//	}
//
//}