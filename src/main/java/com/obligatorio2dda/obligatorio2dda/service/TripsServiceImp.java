package com.obligatorio2dda.obligatorio2dda.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.obligatorio2dda.obligatorio2dda.repository.TripsRepository;
import com.obligatorio2dda.obligatorio2dda.entity.Trips;

@Service
public class TripsServiceImp implements TripsService{
    @Autowired
    private TripsRepository tripsRepository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<Trips> findAll(){
        return tripsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Trips findById(Long Id){
        Iterable<Trips> listaTrips = tripsRepository.findAll();
        for (Trips trips : listaTrips) {
            if(trips.getId().equals(Id)){
                return trips;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public Trips save(Trips save){
        return tripsRepository.save(save);
    }

    @Override
    @Transactional
    public void deleteById(Long Id){
        tripsRepository.deleteById(Id);
    }
    
    @Transactional
    public void deleteViajeClienteById(Long idC, Long idV){
        tripsRepository.deleteViajeClienteById(idC, idV);
    }

    @Transactional
    public void deleteAllViajesClienteById(Long id){
        tripsRepository.deleteAllViajesClienteById(id);
    }

    @Override
    public Page<Trips> findAll(Pageable pageable){
        return null;
    }
}
