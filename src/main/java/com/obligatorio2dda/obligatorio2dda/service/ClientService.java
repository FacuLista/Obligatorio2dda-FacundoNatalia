package com.obligatorio2dda.obligatorio2dda.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.obligatorio2dda.obligatorio2dda.entity.Client;

public interface ClientService {

    public Iterable<Client> findAll();  // Buscar Todos los clientes
    public Client findById(Long Ci);    // Buscar por id
    public Client save(Client client);  // Guardar el cliente
    public void deleteById(Long Ci);    // Borrar uno por id
    public Page<Client> findAll(Pageable pageable); // Buscar todos en el pageObject

}
