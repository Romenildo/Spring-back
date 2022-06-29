package com.Ktr.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Autowired
    private BCryptPasswordEncoder encoder;


    /*
     * Funcao encontrar o item por id
     * Busca o objeto no banco de dados, caso encontrar ele retorna o objeto,
     * senão o objeto retornara null, então lançar uma exceção de objeto nao encontrado para o response
     */

    public Cliente findById(Integer id){
        //Optional devido o jpa retorna um objeto do tipo Optional caso ele encontre ou nao o item no banco
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! id : "+ id));
    }


    /*
     * Listar todos os objetos cadastrados no banco
     */
    public List<Cliente> findAll(){
        return repository.findAll();
    }

    /*
     * Criar um novo objeto no banco de dados
     * colcoa o id como null cao algum erro, e id é gerado automaticamente ao criar um novo objeto
     */
    public Cliente create(ClienteDTO objDTO){
        objDTO.setId(null);
        objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        validarCpfeEmail(objDTO);
        Cliente  newObj = new Cliente(objDTO);
        return repository.save(newObj);

    }

    /*
     * Validacao de cpf e email
     * so pegar o cpf e email recebidos 
     */

    private void validarCpfeEmail(ClienteDTO objDTO) {
        /* 
         * caso id seja igual ao passado, quer dizer que ele vai atualizar, por isso 
         * que verifica os dois caso cpf repetido mas quer atualizar algo
        */
        
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if(obj.isPresent() && obj.get().getId() != objDTO.getId()){
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
        }

        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if(obj.isPresent() && obj.get().getId() != objDTO.getId()){
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema");
        }

        
    }

    /*
     * Atualizar um objeto
     * seta o id recebido para garantir que o id seja o mesmo recebido
     * então procurar se o objeto existe, caso não exista joga a exceção de nao encontrado
     * 
     * então criar um novo objeto, com os parametros recebidos
     * 
     * o @Valid é do validations ele lança uma exceção antes mesmo da requisicao ser feita
     * caso o objeto tenha algum valor nulo, ou alguma restricao do validations
     * Essas validacoes são colocadas no DTO como : @NotNull(message = "O campo XX é requerido") antes das variaveis
     */

    public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);//pega o antigo objeto 
        validarCpfeEmail(objDTO);

        oldObj = new Cliente(objDTO);//entao o antigo recebe o novo
        return repository.save(oldObj);
    }

    /*
     * Deletar um objeto
     * So encontrar o objeto, caso o objeto for encontrado delete
     * (não precisa retornar nada)
     */
    public void delete(Integer id) {
        Cliente obj = findById(id);
        //caso o cliente esteja em alguma chamada ele nao pode ser deletado
        if(obj.getChamados().size()>0){
            throw new DataIntegrityViolationException("Ordens de servico ativas e nao pode ser deletado");
        }
        repository.deleteById(id);
    }

}

