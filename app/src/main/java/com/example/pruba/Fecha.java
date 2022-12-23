package com.example.pruba;

public class Fecha {
    private String titulo;
    private String evento;
    private String lugar;
    private String importancia;
    private String observacion;
    private String tiempo;


    public  Fecha(){}

    public Fecha(String titulo, String evento, String lugar, String importancia, String observacion, String tiempo) {
        this.titulo = titulo;
        this.evento = evento;
        this.lugar = lugar;
        this.importancia = importancia;
        this.observacion = observacion;
        this.tiempo = tiempo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getImportancia() {
        return importancia;
    }

    public void setImportancia(String importancia) {
        this.importancia = importancia;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }
}
