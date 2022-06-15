package com.Ktr.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ktr.helpdesk.domain.Tecnico;
import com.Ktr.helpdesk.domain.dtos.TecnicoDTO;
import com.Ktr.helpdesk.repositories.TecnicoRepository;
import com.Ktr.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {
    
    @Autowired
    private TecnicoRepository repository;

    public Tecnico findById(Integer id){
        //Optional devido o jpa retorna um objeto do tipo Optional caso ele encontre ou nao o item no banco
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! id : "+ id));
    }

    public List<Tecnico> findAll(){
        return repository.findAll();
    }

    public Tecnico create(TecnicoDTO objDTO){
        objDTO.setId(null);
        Tecnico  newObj = new Tecnico(objDTO);
        return repository.save(newObj);

    }

}
