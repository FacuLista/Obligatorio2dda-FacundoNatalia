package com.obligatorio2dda.obligatorio2dda.service;

import com.obligatorio2dda.obligatorio2dda.repository.ClientRepository;
import com.obligatorio2dda.obligatorio2dda.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientServiceImp implements ClientService{
    @Autowired
    private ClientRepository clientRepository;

    @Override //Sobreescribir
    @Transactional(readOnly = true)
    public Iterable<Client> findAll(){
        return clientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true) // se utiliza transactional cuando solo se va a hacer una consulta, en  este caso solo de lectura
    public Client findById(Long Ci)
    {
        //Iterable es que tiene la capacidad de repetirse
        Iterable<Client> listaClients = clientRepository.findAll();
        for (Client client : listaClients) {
            if(client.getCi().equals(Ci)){
                return client;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public Client save(Client client)
    {
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public void deleteById(Long Ci)
    {
        clientRepository.deleteById(Ci);
    }

    @Override
    public Page<Client> findAll(Pageable pageable)
    {
        return null;
    }
}
