package com.Ktr.helpdesk.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @PostMapping
    public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO objDTO){
        Chamado newObj = service.create(objDTO);

        //ao criar um novo objeto é aconselhavel retornar a URL referente a ele no banco de dados que normalmente é o id
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ChamadoDTO> update(@PathVariable Integer id,@Valid @RequestBody ChamadoDTO objDTO){

        Chamado obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new ChamadoDTO(obj));
    }

    //Nao vai exister deletado dvido que o que ira mudar é o status da chamada
    //entre aberto e finaliado ao invez de excluilo
   
}
