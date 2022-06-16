package com.Ktr.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ktr.helpdesk.domain.Chamado;
import com.Ktr.helpdesk.repositories.ChamadoRepository;
import com.Ktr.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {
    @Autowired
    private ChamadoRepository repository;

    public Chamado findById(Integer id){
        //Optional devido o jpa retorna um objeto do tipo Optional caso ele encontre ou nao o item no banco
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! id : "+ id));
    }

    public List<Chamado> findAll(){
        return repository.findAll();
    }
}
