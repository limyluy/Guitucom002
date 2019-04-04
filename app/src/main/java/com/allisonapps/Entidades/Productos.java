package com.allisonapps.Entidades;

import java.util.List;

public class Productos {


    private String nombre;
    private String descripcion;
    private String Marca;
    private String imgProducto;
    private String infAdicional;
    private String desAdicional;
    private Boolean oferta;
    private List<String> localesTiene;


    public Productos() {
    }

    public Productos(String nombre, String descripcion, String marca, String imgProducto, String infAdicional, String desAdicional, Boolean oferta, List<String> localesTiene) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        Marca = marca;
        this.imgProducto = imgProducto;
        this.infAdicional = infAdicional;
        this.desAdicional = desAdicional;
        this.oferta = oferta;
        this.localesTiene = localesTiene;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getImgProducto() {
        return imgProducto;
    }

    public void setImgProducto(String imgProducto) {
        this.imgProducto = imgProducto;
    }

    public String getInfAdicional() {
        return infAdicional;
    }

    public void setInfAdicional(String infAdicional) {
        this.infAdicional = infAdicional;
    }

    public String getDesAdicional() {
        return desAdicional;
    }

    public void setDesAdicional(String desAdicional) {
        this.desAdicional = desAdicional;
    }

    public Boolean getOferta() {
        return oferta;
    }

    public void setOferta(Boolean oferta) {
        this.oferta = oferta;
    }

    public List<String> getLocalesTiene() {
        return localesTiene;
    }

    public void setLocalesTiene(List<String> localesTiene) {
        this.localesTiene = localesTiene;
    }
}
