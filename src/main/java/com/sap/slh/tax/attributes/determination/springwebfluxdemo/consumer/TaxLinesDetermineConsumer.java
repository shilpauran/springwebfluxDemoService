package com.sap.slh.tax.attributes.determination.springwebfluxdemo.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.slh.tax.attributes.determination.springwebfluxdemo.model.TaxDetails;
import com.sap.slh.tax.attributes.determination.springwebfluxdemo.model.TaxLine;
import com.sap.slh.tax.attributes.determination.springwebfluxdemo.service.TaxLineService;
import com.sap.slh.tax.attributes.determination.springwebfluxdemo.util.JsonUtil;

import reactor.core.publisher.Flux;

@Component
public class TaxLinesDetermineConsumer {
	
	private static final Logger log = LoggerFactory.getLogger(TaxLinesDetermineConsumer.class);
	
	@Autowired
	private TaxLineService taxLineService;
	
	@RabbitListener(queues = "${rabbitmq.txs.queue}", returnExceptions = "true")
	public Flux<TaxLine> handleMessages(Message requestMessage)
	{
		TaxDetails request = JsonUtil.toObjectFromByte(requestMessage.getBody(),TaxDetails.class);
		Flux<TaxLine> result = taxLineService.determineTaxLines(request);
		log.error("received result from redis is{}",JsonUtil.toJsonString(result));
//		MessageProperties messageProperties = new MessageProperties();
//		Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
//		Message sentMsg = converter.toMessage(result, messageProperties);
		return result;	
	}

}
