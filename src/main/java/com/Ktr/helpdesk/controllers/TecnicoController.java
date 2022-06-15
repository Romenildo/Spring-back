package com.Ktr.helpdesk.controllers;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ktr.helpdesk.domain.Tecnico;
import com.Ktr.helpdesk.domain.dtos.TecnicoDTO;
import com.Ktr.helpdesk.services.TecnicoService;

@RestController
@RequestMapping(value = "/tecnicos")//localhost:8080/tecnicos
public class TecnicoController {

    @Autowired
    private TecnicoService service;

    /* 
     * ResponseEntity é responsavel pelo head e body da requisição
     * é uma boa pratica quando trabalha com web para o retorno do response da requisição
     */

    @GetMapping(value = "/{id}")//localhost:8080/tecnicos/1
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id){//pathVariable quer dizer que a variavel vai vim pela url
         
        Tecnico obj = service.findById(id);
        return ResponseEntity.ok().body(new TecnicoDTO(obj));
    }
    
    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll(){

        List<Tecnico> list = service.findAll();
        //transformar a lista de tecnicos em tecnicosDTO
        List<TecnicoDTO> listDTO = list.stream().map(obj-> new TecnicoDTO(obj)).collect(Collectors.toList());
        //percorre toda a lista, criando um novo objeto dto com o tecnico,      depois transforma eles em uma lista

        return ResponseEntity.ok().body(listDTO);
    }
}
