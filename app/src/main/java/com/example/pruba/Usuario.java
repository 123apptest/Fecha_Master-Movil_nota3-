package com.example.pruba;

public class Usuario {
    private String rut;
    private String clave;
    private String pregunta;
    private String contesta;

    public Usuario(){}

    public Usuario(String rut, String clave, String pregunta, String contesta) {
        this.rut = rut;
        this.clave = clave;
        this.pregunta = pregunta;
        this.contesta = contesta;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getContesta() {
        return contesta;
    }

    public void setContesta(String contesta) {
        this.contesta = contesta;
    }
}
