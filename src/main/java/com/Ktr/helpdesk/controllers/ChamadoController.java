package com.Ktr.helpdesk.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ktr.helpdesk.domain.Chamado;
import com.Ktr.helpdesk.domain.dtos.ChamadoDTO;
import com.Ktr.helpdesk.services.ChamadoService;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoController {
    
    @Autowired
    private ChamadoService service;
    
    @GetMapping(value = "/{id}")//localhost:8080/chamados/1
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id){//pathVariable quer dizer que a variavel vai vim pela url
         
        Chamado obj = service.findById(id);
        return ResponseEntity.ok().body(new ChamadoDTO(obj));
    }

    @GetMapping
    public ResponseEntity<List<ChamadoDTO>> findAll(){

        List<Chamado> list = service.findAll();
        //transformar a lista de Chamados em chamadosDTO
        List<ChamadoDTO> listDTO = list.stream().map(obj-> new ChamadoDTO(obj)).collect(Collectors.toList());
        //percorre toda a lista, criando um novo objeto dto com o Chamado,      depois transforma eles em uma lista

        return ResponseEntity.ok().body(listDTO);
    }
}
