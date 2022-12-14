package com.obligatorio2dda.obligatorio2dda.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.*;

@Entity(name="clientes")
@Inheritance(strategy = InheritanceType.JOINED)
public class Client implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(10000000) // chekeamos los digitos ci
    @Max(99999999)
    // @Size(min = 7, max = 8) // message ="The length of the ci be between 7 and 8
    // numbers"
    @NotNull(message = "Debe ingresar su cedula sin punto ni giones")
    @Column(unique = true, length = 8, nullable = false)
    private Long ci;

    @NotBlank(message = "Ingrese su nombre") // valida que un valor no este en blanco
    @Column(length = 30)
    private String firstname;

    @Column(length = 30)
    private String lastname;

    @Column(name = "mail", nullable = false, length = 30, unique = true)
    private String email;

    @Column(length = 8, nullable = false)
    private String tipo = "Standar";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCi() {
        return ci;
    }

    public void setCi(Long ci) {
        this.ci = ci;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Client(Long id, Long ci,
            String firstname, String lastname, String email, String tipo) {
        this.id = id;
        this.ci = ci;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.tipo = tipo;

    }

    public Client() {
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "client_travel", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "trips_id"))

    private Set<Trips> viajes = new HashSet<>();

    public void setViajes(Set<Trips> viajes) {
        this.viajes = viajes;
    }

    public Set<Trips> getViajes() {
        return viajes;
    }

    public void addTrips(Trips trips) {
        this.viajes.add(trips);
        trips.getClientes().add(this);
    }
}
