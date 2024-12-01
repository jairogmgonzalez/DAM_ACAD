package com.persistencia.objetos.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "direcciones")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "calle", length = 255, nullable = false)
    private String calle;

    @Column(name = "ciudad", length = 50, nullable = false)
    private String ciudad;

    @Column(name = "codigo_postal", length = 5, nullable = false)
    private Integer codigoPostal;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Constructor por defecto
    public Direccion() {}

    // Construtor por parámetros completo
    public Direccion(String calle, String ciudad, Integer codigoPostal, Cliente cliente){
        this.calle = calle;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        setCliente(cliente);
    }

    // Getters y setters
    public Long getId(){
        return this.id;
    }

    public String getCalle(){
        return this.calle;
    }

    public void setCalle(String calle){
        this.calle = calle;
    }

    public String getCiudad(){
        return this.ciudad;
    }

    public void setCiudad(String ciudad){
        this.ciudad = ciudad;
    }

    public Integer getCodigoPostal(){
        return this.codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal){
        this.codigoPostal = codigoPostal;
    }

    public Cliente getCliente(){
        return this.cliente;
    }

    public void setCliente(Cliente cliente){
        this.cliente = cliente;

        if (cliente != null && cliente.getDireccion() != this){
            cliente.setDireccion(this);
        }
    }

    // Método equals
    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Direccion that = (Direccion) other;
        return id.equals(that.id);
    }

    // Método hashCode
    @Override
    public int hashCode(){
        return id.hashCode();
    }
    
}
