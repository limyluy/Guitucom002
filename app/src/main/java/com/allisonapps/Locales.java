package com.allisonapps;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class Locales {

    private String nombre;
    private String descripcion;
    private String logo;
    private String imagen;
    private boolean garage;
    private boolean targeta;
    private boolean garantia;
    private GeoPoint geoPoint;
    private long mgeusta;
    private String fondoColor;
    private int atencion;
    private int precio;
    private int calidad;

    public Locales() {
    }

    public Locales(String nombre, String descripcion, String logo, String imagen, boolean garage, boolean targeta, boolean garantia, GeoPoint geoPoint, long mgeusta, String fondoColor, int atencion, int precio, int calidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.logo = logo;
        this.imagen = imagen;
        this.garage = garage;
        this.targeta = targeta;
        this.garantia = garantia;
        this.geoPoint = geoPoint;
        this.mgeusta = mgeusta;
        this.fondoColor = fondoColor;
        this.atencion = atencion;
        this.precio = precio;
        this.calidad = calidad;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isGarage() {
        return garage;
    }

    public void setGarage(boolean garage) {
        this.garage = garage;
    }

    public boolean isTargeta() {
        return targeta;
    }

    public void setTargeta(boolean targeta) {
        this.targeta = targeta;
    }

    public boolean isGarantia() {
        return garantia;
    }

    public void setGarantia(boolean garantia) {
        this.garantia = garantia;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public long getMgeusta() {
        return mgeusta;
    }

    public void setMgeusta(long mgeusta) {
        this.mgeusta = mgeusta;
    }

    public String getFondoColor() {
        return fondoColor;
    }

    public void setFondoColor(String fondoColor) {
        this.fondoColor = fondoColor;
    }

    public int getAtencion() {
        return atencion;
    }

    public void setAtencion(int atencion) {
        this.atencion = atencion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCalidad() {
        return calidad;
    }

    public void setCalidad(int calidad) {
        this.calidad = calidad;
    }
}
