package com.obligatorio2dda.obligatorio2dda.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.obligatorio2dda.obligatorio2dda.entity.Trips;

@Repository
public interface TripsRepository extends JpaRepository<Trips, Long>{
    @Query(value = "SELECT v.* FROM viajes v WHERE v.eliminado = false", nativeQuery = true)
    public Iterable<Trips> findAllFalse();

    @Query(value = "SELECT v.* FROM viajes v WHERE v.id NOT IN (SELECT v.id FROM viajes v INNER JOIN client_travel ct ON v.id = ct.trips_id WHERE v.eliminado = false AND ct.client_id = :clienteId)", nativeQuery = true)
    public Iterable<Trips> findViajesNotInViajesCliente(@Param("clienteId") Long clienteId);

    @Query(value = "SELECT v.* FROM viajes v INNER JOIN client_travel ct ON v.id = ct.trips_id WHERE v.eliminado = false AND ct.client_id = :clienteId", nativeQuery = true)
    public List<Trips> findViajesByClienteId(@Param("clienteId") Long clienteId);

    @Modifying
    @Query(value = "DELETE FROM client_travel WHERE client_id = :clienteId AND trips_id = :viajeId", nativeQuery = true)
    public void deleteViajeClienteById(@Param("clienteId") Long clienteId, @Param("viajeId") Long viajeId);


    @Modifying
    @Query(value = "DELETE FROM client_travel WHERE client_id = :clienteId", nativeQuery = true)
    public void deleteAllViajesClienteById(@Param("clienteId") Long clienteId);

    @Query(value = "SELECT v.* FROM viajes v INNER JOIN client_travel ct ON v.id = ct.trips_id WHERE v.fecha > :fechaViaje AND ct.client_id = :clienteId ORDER BY v.fecha LIMIT 1", nativeQuery = true)
    public Trips findViajeByClienteIdAndViajeFecha(@Param("fechaViaje") Date fechaViaje, @Param("clienteId") Long clienteId);
}
