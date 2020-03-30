package com.sap.slh.tax.attributes.determination.springwebfluxdemo.config;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class ReactiveRabbitmqConfig {
	
	@Autowired
	private AmqpAdmin amqpAdmin;

	@Value("${rabbitmq.txs.queue}")
	public String queueName;

	@Value("${rabbitmq.txs.exchange}")
	public String exchangeName;

	@Value("${rabbitmq.txs.routingKey}")
	public String routingKey;

	@PostConstruct
	public void initializeQueue() {
		TopicExchange taxServiceTopicExchange = (TopicExchange) ExchangeBuilder.topicExchange(exchangeName)
				.durable(true).build();
		Queue taxAttributesDeterminationQueue = QueueBuilder.durable(queueName).build();
		Binding binding = BindingBuilder.bind(taxAttributesDeterminationQueue).to(taxServiceTopicExchange)
				.with(routingKey);
		amqpAdmin.declareExchange(taxServiceTopicExchange);
		amqpAdmin.declareQueue(taxAttributesDeterminationQueue);
		amqpAdmin.declareBinding(binding);
	}
	
//	@Bean
//	public ConnectionFactory connectionFactory() {
//	return new CachingConnectionFactory();
//	}
//
//	 @Bean
//	    AsyncRabbitTemplate template() {
//		 RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
//		 rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
////		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
////		container.setQueueNames("tax.webflux.reactor.queue1");
//	        return new AsyncRabbitTemplate(rabbitTemplate);
//	    }


}
