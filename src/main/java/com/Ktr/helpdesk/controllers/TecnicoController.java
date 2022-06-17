package com.Ktr.helpdesk.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Ktr.helpdesk.domain.Tecnico;
import com.Ktr.helpdesk.domain.dtos.TecnicoDTO;
import com.Ktr.helpdesk.services.TecnicoService;
/*
 * Classe de controle para as chamadas de requisicoes de tecnicos
 * Ao fazer um request com http://localhost:8080/tecnicos irá ser direcionado para esses controladores
 * A partir dele tem a ligação com os demais camadas de serviços e os dados no banco de dados
 * Enfim retornando a resposta para o requirente
 * 
 * @Autowired (injecção de dependencias) é para instancias o objeto, mas tirando a responsabilidade do controller
 * e deixando somente na camada de servicos. Tornando assim seus atributos e métodos para o controller com service.findAll()...
 *
 * - ResponseEntity<Object> É um recurso de (boa pratica() com requisições, é do proprio http o qual é responsavel
 * pelaas response retornada para o cliente, nele pode definir o tipo do retorno controlando o body e o header 
 * 
 * -ObjetoDTO é uma camada a mais entre o cliente e as entidades, fazendo com que as informações que sejam manipuladas e enviadas
 * não sejam aquelas da API e manipulação no banco de dados.(Essa camada de DTO é somente Utilizada no controller)
 * 
 * 
 */
@RestController
@RequestMapping(value = "/tecnicos")//localhost:8080/tecnicos
public class TecnicoController {

    @Autowired
    private TecnicoService service;


    @GetMapping(value = "/{id}")//localhost:8080/tecnicos/1
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id){//pathVariable quer dizer que a variavel vai vim pela url
         /*
          * Para a função de retornar um objeto pelo id é necessario que:
          * 1º Fazer uma busca com findById para encontrar se o id se encontra no banco ou nao
          * A camada de service que deve ser responsavel por jogar a exceção caso nao encontrar o id
          */
        Tecnico obj = service.findById(id);
        return ResponseEntity.ok().body(new TecnicoDTO(obj));//o retorno é um objetoDTO com as informacoes do objeto do id
    }

    
    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll(){
         /*
         *A função de retornar uma lista de Objetos 
         *  1º retornar uma lista dos objetos a partir do service
         *  2º transformar essa lista para uma lista de objetosDTOS que serão retornados 
         */
        List<Tecnico> list = service.findAll();
        List<TecnicoDTO> listDTO = list.stream().map(obj-> new TecnicoDTO(obj)).collect(Collectors.toList());
       //essa linha faz um map() de cada objeto da lista do objeto, chamando para retornar um ObjetoDTO
        // de cada instancia da lista original, enfim retornando a collect que junta eles com array de DTOS

        return ResponseEntity.ok().body(listDTO);//retorna a lista de DTOS
    }


    //adicionar a autorizacao necessaria para a requisicao
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO){//@RequestBody é dizendo que o objeto vai vim no body da requisiçao
                                                                                    //@Valid é para validar os dados colocados na classe DTO ex: @NotNull(message = "O campo NOME é requerido")

        /**
        * A funçao de criar um novo objeto no banco de dados deve:
        * 1º instanciar um novo Objeto com as propriedades recebidas como parametro no body da requisição
        *o @Valid intercepta os dados antes de entrar na função e ja faz a validação se os campos estiver vazio
        */
        Tecnico newObj = service.create(objDTO);
        
        //ao criar um novo objeto é aconselhavel retornar a URL referente a ele no banco de dados que normalmente é o id
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    //adicionar a autorizacao necessaria para a requisicao
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id,@Valid @RequestBody TecnicoDTO objDTO){
        //o objeto vai receber o @pathVariable que quer dizer que ira receber o id pela URL, e o @requestBody o objeto vem pelo Body da requisicao

        /*
         * Para atualizar um objeto
         * A camada de Service que faz todo o processo, ela so deve ser chamada aqui;
         */
        Tecnico obj = service.update(id, objDTO);//recebe o id para atualizar e o objeto recebido e retornma um objeto normal
        return ResponseEntity.ok().body(new TecnicoDTO(obj));//no retorno deve criar um novo objeto DTO a partir do objeto atualizado
    }


    //adicionar a autorizacao necessaria para a requisicao
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> delete(@PathVariable Integer id){//@pathVariable identifica que a variavel vai vim pela URL
        /*
         * Para deletar um objeto
         * A camada de Service deve ser responsavel pela verificacao se o id está no banco ou nao
         * se nao tiver deve lançar a exceção, se nao então so deletar.
         */
        service.delete(id);
        return ResponseEntity.noContent().build();//retorna um noContent (nada)
    }

}
