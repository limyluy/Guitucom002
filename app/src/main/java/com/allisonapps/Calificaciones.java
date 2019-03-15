package com.allisonapps;

public class Calificaciones {

    private int atencion;
    private int calidad;
    private int precio;

    public Calificaciones() {
    }

    public Calificaciones(int atencion, int calidad, int precio) {
        this.atencion = atencion;
        this.calidad = calidad;
        this.precio = precio;
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
}
