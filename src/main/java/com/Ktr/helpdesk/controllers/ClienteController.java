package com.Ktr.helpdesk.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Ktr.helpdesk.domain.Cliente;
import com.Ktr.helpdesk.domain.dtos.ClienteDTO;
import com.Ktr.helpdesk.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")//localhost:8080/clientes
public class ClienteController {

    @Autowired
    private ClienteService service;

    /* 
     * ResponseEntity é responsavel pelo head e body da requisição
     * é uma boa pratica quando trabalha com web para o retorno do response da requisição
     */

    @GetMapping(value = "/{id}")//localhost:8080/tecnicos/1
    public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id){//pathVariable quer dizer que a variavel vai vim pela url
         
        Cliente obj = service.findById(id);
        return ResponseEntity.ok().body(new ClienteDTO(obj));
    }
    
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll(){

        List<Cliente> list = service.findAll();
        //transformar a lista de tecnicos em tecnicosDTO
        List<ClienteDTO> listDTO = list.stream().map(obj-> new ClienteDTO(obj)).collect(Collectors.toList());
        //percorre toda a lista, criando um novo objeto dto com o Cliente,      depois transforma eles em uma lista

        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDTO){
        Cliente newObj = service.create(objDTO);

        //ao criar um novo objeto é aconselhavel retornar a URL referente a ele no banco de dados que normalmente é o id
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Integer id,@Valid @RequestBody ClienteDTO objDTO){

        Cliente obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new ClienteDTO(obj));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
