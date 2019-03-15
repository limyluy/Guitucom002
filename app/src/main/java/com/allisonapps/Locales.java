package com.allisonapps;

import com.google.firebase.firestore.GeoPoint;

public class Locales {

    private String nombre;
    private String decripcion;
    private String logo;
    private String imagen;
    private boolean garage;
    private boolean targeta;
    private boolean garantia;
    private GeoPoint geoPoint;
    private long mgeusta;
    private String fondoColor;

    public Locales() {
    }

    public Locales(String nombre, String decripcion, String logo, String imagen, boolean garage, boolean targeta, boolean garantia, GeoPoint geoPoint, long mgeusta, String fondoColor) {
        this.nombre = nombre;
        this.decripcion = decripcion;
        this.logo = logo;
        this.imagen = imagen;
        this.garage = garage;
        this.targeta = targeta;
        this.garantia = garantia;
        this.geoPoint = geoPoint;
        this.mgeusta = mgeusta;
        this.fondoColor = fondoColor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDecripcion() {
        return decripcion;
    }

    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
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
}
