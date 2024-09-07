package com.example.lab2_20212607.bean;

import java.io.Serializable;

public class Jugador implements Serializable {

    private String nombre;


    public Jugador() {

    }

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
