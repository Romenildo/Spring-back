package com.Ktr.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ktr.helpdesk.domain.Chamado;
import com.Ktr.helpdesk.domain.Cliente;
import com.Ktr.helpdesk.domain.Tecnico;
import com.Ktr.helpdesk.domain.dtos.ChamadoDTO;
import com.Ktr.helpdesk.domain.enums.Prioridade;
import com.Ktr.helpdesk.domain.enums.Status;
import com.Ktr.helpdesk.repositories.ChamadoRepository;
import com.Ktr.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository repository;
    @Autowired
    private TecnicoService tecnicoService;
    @Autowired
    private ClienteService clienteService;


    /*
     * Funcao encontrar o item por id
     * Busca o objeto no banco de dados, caso encontrar ele retorna o objeto,
     * senão o objeto retornara null, então lançar uma exceção de objeto nao encontrado para o response
     */
    public Chamado findById(Integer id){
        //Optional devido o jpa retorna um objeto do tipo Optional caso ele encontre ou nao o item no banco
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! id : "+ id));
    }

    /*
     * Listar todos os objetos cadastrados no banco
     */
    public List<Chamado> findAll(){
        return repository.findAll();
    }

    /*
     * Criar um novo objeto no banco de dados
     * o @Valid é do validations ele lança uma exceção antes mesmo da requisicao ser feita
     * caso o objeto tenha algum valor nulo, ou alguma restricao do validations
     * Essas validacoes são colocadas no DTO como : @NotNull(message = "O campo XX é requerido") antes das variaveis
     */
    public Chamado create (@Valid ChamadoDTO objDTO){
        return repository.save(newChamado(objDTO));
    }

    /*
     * Atualizar um objeto
     * seta o id no novo item passado então troca os dados dele para o novo
     */
    
    public Chamado update(Integer id, @Valid ChamadoDTO objDTO){
       
        objDTO.setId(id); //garantir que o objeto passado tenha o mesmo id do pedido
        Chamado oldObj = this.findById(id);//verificar se o id está no banco ou nao(findbyid lança excesao e cancela o resto se objeto nao estiver)

        oldObj = newChamado(objDTO);//antigo vai receber o novo
        return repository.save(oldObj);
    }

    /*
     * Como o chamado é um objeto que tem relacao com outros dois objetos
     * É precio alterar eles em todos os seus relacionados
     * Tecnico e cliente devem ser instanciados e então colocados no chamado
     */
    
    private Chamado newChamado(ChamadoDTO obj){

        //instanciar o tecnico e o cliente da chamada
        Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
        Cliente cliente = clienteService.findById(obj.getCliente());

        Chamado chamado = new Chamado();
        //caso o objeto passado seja para atualizar ele vai pegar o id dele e colocar no novo
        if(obj.getId()!= null){
            chamado.setId(obj.getId());
        }
        //caso o status do pedido de atualizar seja =2 (Encerrado) ele marca a hora que fechou
        if(obj.getStatus().equals(2)){
            chamado.setDataFechamento(LocalDate.now());
        }

        //chamado recebe o tecnico e o cliente referente a ele
        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        //como a prioridade salva no chamado é o codigo, basta procurar pelo codigo e gerar a prioridade
        chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
        chamado.setStatus(Status.toEnum(obj.getStatus()));
        
        chamado.setTitulo(obj.getTitulo());
        chamado.setObservacoes(obj.getObservacoes());
        return chamado;

    }
}
