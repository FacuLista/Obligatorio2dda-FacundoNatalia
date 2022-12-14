package com.obligatorio2dda.obligatorio2dda.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.obligatorio2dda.obligatorio2dda.entity.Client;

public interface ClientService {

    public Iterable<Client> findAll();
    public Client findById(Long Ci);
    public Client save(Client client);
    public void deleteById(Long Ci);
    public Page<Client> findAll(Pageable pageable);

}
