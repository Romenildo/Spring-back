package com.Ktr.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ktr.helpdesk.domain.Pessoa;
import com.Ktr.helpdesk.domain.Cliente;
import com.Ktr.helpdesk.domain.dtos.ClienteDTO;
import com.Ktr.helpdesk.repositories.ClienteRepository;
import com.Ktr.helpdesk.repositories.PessoaRepository;
import com.Ktr.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.Ktr.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;

    public Cliente findById(Integer id){
        //Optional devido o jpa retorna um objeto do tipo Optional caso ele encontre ou nao o item no banco
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! id : "+ id));
    }

    public List<Cliente> findAll(){
        return repository.findAll();
    }

    public Cliente create(ClienteDTO objDTO){
        objDTO.setId(null);
        validarCpfeEmail(objDTO);
        Cliente  newObj = new Cliente(objDTO);
        return repository.save(newObj);

    }

    private void validarCpfeEmail(ClienteDTO objDTO) {

        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if(obj.isPresent() && obj.get().getId() != objDTO.getId()){
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
        }

        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if(obj.isPresent() && obj.get().getId() != objDTO.getId()){
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema");
        }

        //caso id seja igual ao passado, quer dizer que ele vai atualizar, por isso 
        //que verifica os dois caso cpf repetido mas quer atualizar algo
    }

    public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);
        validarCpfeEmail(objDTO);
        oldObj = new Cliente(objDTO);
        return repository.save(oldObj);
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        if(obj.getChamados().size()>0){
            throw new DataIntegrityViolationException("Ordens de servico ativas e nao pode ser deletado");
        }
        repository.deleteById(id);
    }

}

