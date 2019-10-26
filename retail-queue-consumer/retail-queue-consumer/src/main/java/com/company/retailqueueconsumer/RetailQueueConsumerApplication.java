package com.company.retailqueueconsumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class RetailQueueConsumerApplication {

	public static final String TOPIC_EXCHANGE_NAME = "levelUp-exchange";
	public static final String QUEUE_NAME = "levelUp-queue";
	public static final String ROUTING_KEY = "levelUp.create.#";

	@Bean
	TopicExchange exchange(){
		return new TopicExchange(TOPIC_EXCHANGE_NAME);
	}

	@Bean
	Queue queue(){
		return new Queue(QUEUE_NAME,false);
	}

	@Bean
	Binding binding (Queue queue, TopicExchange exchange){
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
	}

	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
		return new Jackson2JsonMessageConverter();
	}


	public static void main(String[] args) {
		SpringApplication.run(RetailQueueConsumerApplication.class, args);
	}

}
