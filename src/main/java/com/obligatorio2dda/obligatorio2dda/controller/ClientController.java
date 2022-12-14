package com.obligatorio2dda.obligatorio2dda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;


import com.obligatorio2dda.obligatorio2dda.entity.Client;
import com.obligatorio2dda.obligatorio2dda.service.ClientService;
import com.obligatorio2dda.obligatorio2dda.service.ClientServiceImp;
import com.obligatorio2dda.obligatorio2dda.entity.Trips;
import com.obligatorio2dda.obligatorio2dda.service.TripsServiceImp;
import com.obligatorio2dda.obligatorio2dda.repository.TripsRepository;

@Controller
public class ClientController {
    
    @Autowired
  private ClientService clientService;

  @Autowired
  private ClientServiceImp clientServiceImp;

  @Autowired
  private TripsRepository tripsRepository;

  @Autowired
  private TripsServiceImp tripsServiceImp;

  @GetMapping(value = "/listarClients")
  public String listarClients(Model modelo) {
    modelo.addAttribute("clients", clientService.findAll());
    return "listar_clients";
  }

  @GetMapping(value = "/gestionClient")
  public String gestionClient(Model modelo) {
    modelo.addAttribute("client", new Client());
    return "agregar_client";
  }

  @PostMapping(value = "/guardarClient")
  public String guardarClient(@Validated @ModelAttribute("client") Client client, BindingResult bindingResult,
      RedirectAttributes redirect) {
    try {
      Iterable<Client> listaClient = clientService.findAll();
      for (Client cliStan : listaClient) {
        if (cliStan.getCi().equals(client.getCi()) || cliStan.getEmail().equals(client.getEmail())) {
          redirect.addFlashAttribute("msgError", "Error : La cédula o el email del cliente ya existen");
          return "redirect:/gestionClient";
        }
      }
      if (bindingResult.hasErrors()) {
        return "agregar_client";
      }
      clientService.save(client);
      redirect.addFlashAttribute("msgExito", "El cliente fue agregado con exito");
      return "redirect:/listarClients";

    } catch (Exception e) {
      System.out.println(e);
      return "redirect:/listarClients";
    }
  }

  @PostMapping(value = "/modificarClient/{ci}")
  public String modificarClient(@PathVariable(value = "ci") Long ci, @ModelAttribute("client") Client client,
      Model modelo, BindingResult bindingResult, RedirectAttributes redirect) {
    try {
      if(bindingResult.hasErrors()){            
        return "editar_client";
    }
      Client cliStan = clientServiceImp.findById(ci);
      if (cliStan != null) {
        cliStan.setCi(ci);
        cliStan.setFirstname(client.getFirstname());
        cliStan.setLastname(client.getLastname());
        cliStan.setEmail(client.getEmail());
        cliStan.setTipo(client.getTipo());

        clientService.save(cliStan);
        redirect.addFlashAttribute("msgExito", "El cliente ha sido actualizado correctamente");
        return "redirect:/listarClients";
      }
      return "redirect:/listarClients";
    } catch (Exception e) {
      System.out.println(e);
      redirect.addFlashAttribute("msgError", "Ocurrio un error, no se logro modificar, compruebe que los datos sean correctos");
      return "redirect:/listarClients";
    }
  }

  @GetMapping(value = "/cargarClient/{ci}")
  public String cargarClient(@PathVariable(value = "ci") Long ci, Model modelo) {
    try {
      Client client = clientServiceImp.findById(ci);
      if (client != null) {
        modelo.addAttribute("client", client);
        return "editar_client";
      }
      return "redirect:/listarClients";
    } catch (Exception e) {
      return "redirect:/listarClients";
    }
  }

  

  @GetMapping(value = "/cargarAddClient/{ci}")
  public String cargarAddClient(@PathVariable(value = "ci") Long ci, Model modelo, RedirectAttributes redirect) {
    try{
      Client client = clientServiceImp.findById(ci);
    Iterable<Trips> listaTrips = tripsRepository.findViajesNotInViajesCliente(client.getId());
    modelo.addAttribute("client", client);
    modelo.addAttribute("listaTrips", listaTrips);
    return "add_CT";
    }catch(Exception e){
      redirect.addFlashAttribute("msgError", "Error");
      return "redirect:/listarClients";
    }
  }


  @GetMapping(value = "/deleteClient/{ci}")
  public String deleteClient(@PathVariable Long ci) {
    try {
      Client client = clientService.findById(ci);
      tripsServiceImp.deleteAllViajesClienteById(client.getId());
      clientService.deleteById(client.getId());
      return "redirect:/listarClients";
    } catch (Exception e) {
      return "redirect:/listarClients";
    }
  }
}
