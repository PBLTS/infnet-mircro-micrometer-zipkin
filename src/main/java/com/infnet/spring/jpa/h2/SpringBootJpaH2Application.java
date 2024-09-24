package com.infnet.spring.jpa.h2;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.ContainerCustomizer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import brave.Tracing;
import brave.spring.rabbit.SpringRabbitTracing;

@EnableRabbit
@Configuration
@SpringBootApplication
public class SpringBootJpaH2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaH2Application.class, args);
	}


	@Bean
public SpringRabbitTracing springRabbitTracing(Tracing tracing) {
    return SpringRabbitTracing
            .newBuilder(tracing)
            .build();
}

@Bean
public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, SpringRabbitTracing tracing) {
    RabbitTemplate rabbitTemplate = tracing.newRabbitTemplate(connectionFactory);
    return rabbitTemplate;
}

@Bean
public ContainerCustomizer<SimpleMessageListenerContainer> containerCustomizer() {
    // This config is for listener to get tracing id from header
    return container -> container.setObservationEnabled(true);
}

}
