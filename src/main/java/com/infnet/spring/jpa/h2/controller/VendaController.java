package com.infnet.spring.jpa.h2.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infnet.spring.jpa.h2.model.Vendas;
import com.infnet.spring.jpa.h2.repository.VendaRepository;

import io.micrometer.observation.annotation.Observed;




@CrossOrigin(origins = "http://localhost:8081")
@RestController
@Observed
@RequestMapping("/api")
public class VendaController {

  private static final Logger log = LoggerFactory.getLogger(VendaController.class);

  @Autowired
  VendaRepository vendaRepository;


  @GetMapping("/venda/{title}")
  public ResponseEntity<List<Vendas>> getAll(@PathVariable String title) {
    log.info("busca pela venda "+ title);
    log.debug("DEBUG");

    try {
      List<Vendas> vendas = new ArrayList<Vendas>();

      if (title == null)
      vendaRepository.findAll().forEach(vendas::add);
      else
      vendaRepository.findByDescriptionContainingIgnoreCase(title).forEach(vendas::add);

      if (vendas.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      log.debug("DEBUG");
      log.info("busca pela venda - Finalizou "+ title);
      return new ResponseEntity<>(vendas, HttpStatus.OK);
      
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  



}
