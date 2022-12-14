package com.obligatorio2dda.obligatorio2dda.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.obligatorio2dda.obligatorio2dda.entity.Trips;
import com.obligatorio2dda.obligatorio2dda.entity.Client;
import com.obligatorio2dda.obligatorio2dda.service.ClientService;
import com.obligatorio2dda.obligatorio2dda.service.ClientServiceImp;
import com.obligatorio2dda.obligatorio2dda.service.TripsServiceImp;
import com.obligatorio2dda.obligatorio2dda.repository.TripsRepository;

@Controller
public class AddTripsController {

    @Autowired
  private ClientService clientService;

  @Autowired
  private TripsRepository tripsRepository; // Repository

  @Autowired
  private TripsServiceImp tripsServiceImp;

  @Autowired
  private ClientServiceImp clientServiceImp;

  @PostMapping(value = "/saveAddClient/{ci}")
  public String guardarClient(@PathVariable(value = "ci") Long ci, @ModelAttribute("client") Client client,
      Model modelo) {
    try {
      int cont = 0;
      int contDesc = 0;
      Client cliStand = clientServiceImp.findById(ci); 
      List<Trips> listaTrips = tripsRepository.findViajesByClienteId(ci);
      for (int i = 0; i < cliStand.getViajes().size(); i++) {
        contDesc = contDesc + 1;
      }
      if(contDesc >= 3){
        if (cliStand != null) {
          cliStand.setCi(ci);
          cliStand.setId(client.getId());
          cliStand.setFirstname(client.getFirstname());
          cliStand.setLastname(client.getLastname());
          cliStand.setEmail(client.getEmail()); 
          for (Trips tripsAP : client.getViajes()) {
            listaTrips.add(tripsAP);
          }
          for (Trips trips : listaTrips) {
            Double precio = trips.getPrecio() * 0.80;
            trips.setPrecio(precio);
            cliStand.addTrips(trips);
          }
        }
      }else{
        if (cliStand != null) {
          cliStand.setCi(ci);
          cliStand.setId(client.getId());
          cliStand.setFirstname(client.getFirstname());
          cliStand.setLastname(client.getLastname());
          cliStand.setEmail(client.getEmail());
          for (Trips tripsAP : client.getViajes()) {
            listaTrips.add(tripsAP);
          }
          for (Trips trips : listaTrips) {
            cliStand.addTrips(trips);
          }
        }
      }
      cont = cliStand.getViajes().size();
      if(cont >= 3){
        cliStand.setTipo("Vip");
      }else{
        cliStand.setTipo("Estándar");
      }
      clientService.save(cliStand);
      return "redirect:/listarClients";
    } catch (Exception e) {
      System.out.println(e);
      return "add_CT";
    }
  }

  @GetMapping(value = "/cargarTripsClient/{ci}")
  public String listarTripsClient(@PathVariable(value = "ci") Long ci, Model modelo) {
    Client client = clientService.findById(ci);
    Iterable<Trips> listaClientsTrips = tripsRepository.findViajesByClienteId(client.getId());
    modelo.addAttribute("clientsTrips", listaClientsTrips);
    modelo.addAttribute("client", client);
    return "del_CT";
  }

  @GetMapping(value = "/eliminarTrips/{id}/client/{ci}")
  public String eliminarClientTrips(@PathVariable Long ci, @PathVariable Long id) {
    Client client = clientService.findById(ci);
    tripsServiceImp.deleteViajeClienteById(client.getId(), id);
    int cont = client.getViajes().size();
    if(cont < 3){
      client.setTipo("Estándar");
      clientService.save(client);
    }
    return "redirect:/listarClients";
  }
}
