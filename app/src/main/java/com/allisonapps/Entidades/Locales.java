package com.allisonapps.Entidades;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class Locales {

    private String nombre;
    private String direccion;
    private String telefono;
    private String descripcion;
    private GeoPoint ubicacion;
    private int atencion;
    private int calidad;
    private int precio;
    private boolean tarjeta;
    private boolean garaje;
    private boolean garantia;
    private String imgLogo;
    private ArrayList<String> imgLocal;
    private ArrayList<String> etiquetas;
    private ArrayList<String> productos;
    private long numRecomendado;
    private boolean actualizado;
    private String color;
    private boolean ofertas;

    public Locales() {
    }

    public Locales(String nombre, String direccion, String telefono, String descripcion, GeoPoint ubicacion, int atencion, int calidad, int precio, boolean tarjeta, boolean garaje, boolean garantia, String imgLogo, ArrayList<String> imgLocal, ArrayList<String> etiquetas, ArrayList<String> productos, long numRecomendado, boolean actualizado, String color, boolean ofertas) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.atencion = atencion;
        this.calidad = calidad;
        this.precio = precio;
        this.tarjeta = tarjeta;
        this.garaje = garaje;
        this.garantia = garantia;
        this.imgLogo = imgLogo;
        this.imgLocal = imgLocal;
        this.etiquetas = etiquetas;
        this.productos = productos;
        this.numRecomendado = numRecomendado;
        this.actualizado = actualizado;
        this.color = color;
        this.ofertas = ofertas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public GeoPoint getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(GeoPoint ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getAtencion() {
        return atencion;
    }

    public void setAtencion(int atencion) {
        this.atencion = atencion;
    }

    public int getCalidad() {
        return calidad;
    }

    public void setCalidad(int calidad) {
        this.calidad = calidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public boolean isTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(boolean tarjeta) {
        this.tarjeta = tarjeta;
    }

    public boolean isGaraje() {
        return garaje;
    }

    public void setGaraje(boolean garaje) {
        this.garaje = garaje;
    }

    public boolean isGarantia() {
        return garantia;
    }

    public void setGarantia(boolean garantia) {
        this.garantia = garantia;
    }

    public String getImgLogo() {
        return imgLogo;
    }

    public void setImgLogo(String imgLogo) {
        this.imgLogo = imgLogo;
    }

    public ArrayList<String> getImgLocal() {
        return imgLocal;
    }

    public void setImgLocal(ArrayList<String> imgLocal) {
        this.imgLocal = imgLocal;
    }

    public ArrayList<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(ArrayList<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public ArrayList<String> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<String> productos) {
        this.productos = productos;
    }

    public long getNumRecomendado() {
        return numRecomendado;
    }

    public void setNumRecomendado(long numRecomendado) {
        this.numRecomendado = numRecomendado;
    }

    public boolean isActualizado() {
        return actualizado;
    }

    public void setActualizado(boolean actualizado) {
        this.actualizado = actualizado;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isOfertas() {
        return ofertas;
    }

    public void setOfertas(boolean ofertas) {
        this.ofertas = ofertas;
    }
}
