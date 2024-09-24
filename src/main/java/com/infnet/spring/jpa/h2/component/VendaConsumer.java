package com.infnet.spring.jpa.h2.component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.spring.jpa.h2.model.Vendas;
import com.infnet.spring.jpa.h2.repository.VendaRepository;

@Component
public class VendaConsumer {

    @Autowired
    VendaRepository vendaRepository;


    @RabbitListener(queues = {"${queue.name}"})
    public void realiza_venda( @Payload String fileBody){

        try {

            System.out.println("Message " + fileBody);

            ObjectMapper mapper = new ObjectMapper();

            Vendas venda = mapper.readValue(fileBody, Vendas.class);


            vendaRepository.save(venda);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

       
    }

}
