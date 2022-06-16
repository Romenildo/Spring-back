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


    public Chamado findById(Integer id){
        //Optional devido o jpa retorna um objeto do tipo Optional caso ele encontre ou nao o item no banco
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! id : "+ id));
    }

    public List<Chamado> findAll(){
        return repository.findAll();
    }

    public Chamado create (@Valid ChamadoDTO objDTO){
        return repository.save(newChamado(objDTO));
    }
    
    public Chamado update(Integer id, @Valid ChamadoDTO objDTO){
        //garantir que o objeto passado tenha o mesmo id do pedido
        objDTO.setId(id);
        Chamado oldObj = findById(id);//verificar se o id está no banco ou nao
        oldObj = newChamado(objDTO);
        return repository.save(oldObj);
    }
    
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

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        //como a prioridade salva no chamado é o codigo, gasta procurar pelo codigo e gerar a prioridade
        chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
        chamado.setStatus(Status.toEnum(obj.getStatus()));
        
        chamado.setTitulo(obj.getTitulo());
        chamado.setObservacoes(obj.getObservacoes());
        return chamado;

    }
}
