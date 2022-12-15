package com.obligatorio2dda.obligatorio2dda.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.obligatorio2dda.obligatorio2dda.entity.Trips;

public interface TripsService {
    public Iterable<Trips> findAll();   // Buscar todos los viajes
    public Trips findById(Long Id);     // Buscar uno por Id
    public Trips save(Trips viaje);     // Guardar
    public void deleteById(Long Id);    // Borrar por id
    public Page<Trips> findAll(Pageable pageable); // Buscar todos en el pageObject
}
