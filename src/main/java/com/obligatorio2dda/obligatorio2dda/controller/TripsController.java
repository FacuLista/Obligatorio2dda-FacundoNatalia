package com.obligatorio2dda.obligatorio2dda.controller;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;

import com.obligatorio2dda.obligatorio2dda.entity.Trips;
import com.obligatorio2dda.obligatorio2dda.entity.Client;
import com.obligatorio2dda.obligatorio2dda.repository.TripsRepository;
import com.obligatorio2dda.obligatorio2dda.service.ClientService;
import com.obligatorio2dda.obligatorio2dda.service.TripsService;
import com.obligatorio2dda.obligatorio2dda.service.TripsServiceImp;

@Controller
public class TripsController {

    @Autowired
    private TripsService tripsService;

    @Autowired
    private TripsServiceImp tripsServiceImp;

    @Autowired
    private TripsRepository tripsRepository;

    @Autowired
    private ClientService clientService;

    @GetMapping(value = "/listarTrips")
    public String listarTrips(Model modelo){
        modelo.addAttribute("travel", tripsService.findAll());
        return "listar_trips";
    }

    @GetMapping(value = "/gestionTrips")
    public String gestionTrips(Model modelo) {
        modelo.addAttribute("trips", new Trips());
        return "agregar_trips";
    }


    @PostMapping(value = "/guardarTrips")
    public String guardarTrips(@Validated @ModelAttribute("trips") Trips trips, BindingResult bindingResult,
    RedirectAttributes redirect, Model modelo) {
        if(bindingResult.hasErrors())
        {
            return "agregar_trips";
        }
        tripsService.save(trips);
        redirect.addFlashAttribute("msgExito", "El viaje fue agregado con exito");
        return "redirect:/listarTrips";
    }

    @GetMapping(value = "/cargarTrips/{id}")
    public String cargarTrips(@PathVariable(value = "id") Long id, Model modelo) {
        Trips trips = tripsServiceImp.findById(id);
        modelo.addAttribute("trips", trips);
        return "editar_trips";
    }

    @PostMapping(value = "/modificarTrips/{id}")
    public String updateTrips(@ModelAttribute("trips") Trips trips, Model modelo, BindingResult bindingResult, RedirectAttributes redirect){
        try{
            Trips tripsReal = tripsServiceImp.findById(trips.getId());
        if(bindingResult.hasErrors()){            
            return "editar_trips";
        }
        tripsReal.setId(trips.getId());
        tripsReal.setDestino(trips.getDestino());
        tripsReal.setFecha(trips.getFecha());
        tripsReal.setModalidad(trips.getModalidad());
        tripsReal.setPrecio(trips.getPrecio());

        tripsService.save(tripsReal);
        redirect.addFlashAttribute("msgExito", "El viaje ha sido actualizado correctamente");
        return "redirect:/listarTrips";
        }catch(Exception e){
            System.out.println(e);
            redirect.addFlashAttribute("msgError", "Ocurrio un error, no se logro modificar, compruebe que los datos sean correctos");
            return "redirect:/listarTrips";
        }
    }

    @GetMapping(value = "/deleteTrips/{id}")
    public String eliminarTrips(@PathVariable Long id) {
        tripsService.deleteById(id);
        return "redirect:/listarTrips";
    }

    @GetMapping(value = "/ViajeProximo/{ci}")
    public String consultaCliente(@PathVariable(value = "ci") Long ci, Model modelo){
        modelo.addAttribute("ci", ci);
        modelo.addAttribute("trips", new Trips());
        return "viajeProx";
    }

    @RequestMapping(value = "/ViajeProximo/{ci}/Dia/{fecha}")
    public String consultaClienteFecha(@RequestParam(value = "ci", required = false) Long ci, @RequestParam(value = "fecha", required = false) String fecha, Model modelo){
        Client client = clientService.findById(ci);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date fechaConvertida = null;
        try{
            java.util.Date fechaTrips = dateFormat.parse(fecha);
            fechaConvertida = new java.sql.Date(fechaTrips.getTime());
        }catch(Exception e){
            System.out.println(e);
        }
        Trips trips = tripsRepository.findViajeByClienteIdAndViajeFecha(fechaConvertida, client.getId());
        modelo.addAttribute("trips", trips);
        return "viajeProx";
    }
}
