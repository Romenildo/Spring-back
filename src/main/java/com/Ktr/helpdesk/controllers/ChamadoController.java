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

/*
 * Classe de controle para as chamadas de requisicoes de chamados
 * Ao fazer um request com http://localhost:8080/chamados irá ser direcionado para esses controladores
 * A partir del tem a ligação com os demais camadas de serviços e os dados no banco de dados
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
 */
@RestController
@RequestMapping(value = "/chamados")//link de pedidos das requisições é definido no value {{local}}/exemplo
public class ChamadoController {
    
    @Autowired//injeção de dependencias*
    private ChamadoService service;
    
    /**
     * Pegar um Objeto a partir do ID
     */

    @GetMapping(value = "/{id}")//localhost:8080/chamados/1
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id){//@pathVariable quer dizer que a variavel vai vim pela url
         /*
          * Para a função de retornar um objeto pelo id é necessario que:
          * 1º Fazer uma busca com findById para encontrar se o id se encontra no banco ou nao
          * A camada de service que deve ser responsavel por jogar a exceção caso nao encontrar o id
          */
        Chamado obj = service.findById(id);

        return ResponseEntity.ok().body(new ChamadoDTO(obj));//o retorno é um objetoDTO com as informacoes do objeto do id
    }

     /**
     * Retornar todos os Objetos cadastrados
     */
    @GetMapping
    public ResponseEntity<List<ChamadoDTO>> findAll(){
        /*
         *A função de retornar uma lista de Objetos 
         *  1º retornar uma lista dos objetos a partir do service
         *  2º transformar essa lista para uma lista de objetosDTOS que serão retornados 
         */

        List<Chamado> list = service.findAll();
        
        List<ChamadoDTO> listDTO = list.stream().map(obj-> new ChamadoDTO(obj)).collect(Collectors.toList());
        //essa linha faz um map() de cada objeto da lista do objeto, chamando para retornar um ObjetoDTO
        // de cada instancia da lista original, enfim retornando a collect que junta eles com array de DTOS

        return ResponseEntity.ok().body(listDTO);//retorna a lista de DTOS
    }

     /**
     * Criar um novo objeto
     */
    @PostMapping
    public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO objDTO){//@RequestBody é dizendo que o objeto vai vim no body da requisiçao
                                                                                    //@Valid é para validar os dados colocados na classe DTO ex: @NotNull(message = "O campo NOME é requerido")
    
       /**
        * A funçao de criar um novo objeto no banco de dados deve:
        * 1º instanciar um novo Objeto com as propriedades recebidas como parametro no body da requisição
        *o @Valid intercepta os dados antes de entrar na função e ja faz a validação se os campos estiver vazio
        */
       
        Chamado newObj = service.create(objDTO);

        //ao criar um novo objeto é aconselhavel retornar a URL referente a ele no banco de dados que normalmente é o id
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Atualizar um objeto existente
     */

    @PutMapping(value = "/{id}")
    public ResponseEntity<ChamadoDTO> update(@PathVariable Integer id,@Valid @RequestBody ChamadoDTO objDTO){
        //o objeto vai receber o @pathVariable que quer dizer que ira receber o id pela URL, e o @requestBody o objeto vem pelo Body da requisicao

        /*
         * Para atualizar um objeto
         * A camada de Service que faz todo o processo, ela so deve ser chamada aqui;
         */

        Chamado obj = service.update(id, objDTO);//recebe o id para atualizar e o objeto recebido e retornma um objeto normal
        return ResponseEntity.ok().body(new ChamadoDTO(obj));//no retorno deve criar um novo objeto DTO a partir do objeto atualizado
    }

    //Nao vai exister deletado dvido que o que ira mudar é o status da chamada
    //entre aberto e finaliado ao invez de excluilo
   
}
