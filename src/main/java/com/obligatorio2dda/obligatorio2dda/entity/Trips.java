package com.obligatorio2dda.obligatorio2dda.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.*;

@Entity
@Table(name = "viajes")
@SQLDelete(sql = "UPDATE viajes SET eliminado = true WHERE id = ?", check = ResultCheckStyle.NONE)
@Where(clause = "eliminado = false")
public class Trips implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Debe ingresar nombre de destino")
    @Column(length = 50)
    private String destino;
    
    @NotNull(message = "Debe ingresar fecha")
    @Column
    private Date fecha;

    @Pattern(regexp = "Maritimo|Terrestre|Aereo", message = "Debe ingresar Maritimo, Terrestre o Aereo, respetando la mayúscula")
    @Column(length = 9)
    private String modalidad;

    @NotNull(message = "Debe ingresar precio")
    @Column
    private Double precio;

    private boolean eliminado = false;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "viajes")
    @JsonIgnore
    private Set<Client> clientes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Set<Client> getClientes() {
        return clientes;
    }

    public void setClientes(Set<Client> clientes) {
        this.clientes = clientes;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Trips() {
    }

    public Trips(Long id, String destino, Date fecha, String modalidad, Double precio, boolean eliminado) {
        this.id = id;
        this.destino = destino;
        this.fecha = fecha;
        this.modalidad = modalidad;
        this.precio = precio;
        this.eliminado = eliminado;
    }
}
