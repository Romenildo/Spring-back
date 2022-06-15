package com.Ktr.helpdesk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ktr.helpdesk.domain.Tecnico;
import com.Ktr.helpdesk.services.TecnicoService;

@RestController
@RequestMapping(value = "/tecnicos")//localhost:8080/tecnicos
public class TecnicoController {

    @Autowired
    private TecnicoService service;

    @GetMapping(value = "/{id}")//localhost:8080/tecnicos/1
    public ResponseEntity<Tecnico> findById(@PathVariable Integer id){//pathVariable quer dizer que a variavel vai vim pela url
         
        Tecnico obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }
    /* 
     * ResponseEntity é responsavel pelo head e body da requisição
     * é uma boa pratica quando trabalha com web para o retorno do response da requisição
     */
}
