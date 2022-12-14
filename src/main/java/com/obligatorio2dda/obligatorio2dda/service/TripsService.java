package com.obligatorio2dda.obligatorio2dda.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.obligatorio2dda.obligatorio2dda.entity.Trips;

public interface TripsService {
    public Iterable<Trips> findAll();
    public Trips findById(Long Id);
    public Trips save(Trips viaje);
    public void deleteById(Long Id);
    public Page<Trips> findAll(Pageable pageable);  
}
