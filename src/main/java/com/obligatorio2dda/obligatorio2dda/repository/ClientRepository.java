package com.obligatorio2dda.obligatorio2dda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.obligatorio2dda.obligatorio2dda.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
    
}
