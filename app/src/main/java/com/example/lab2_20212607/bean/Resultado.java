package com.example.lab2_20212607.bean;

import java.io.Serializable;

public class Resultado implements Serializable {

    private long tiempo;
    private boolean cancelo;


    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public boolean isCancelo() {
        return cancelo;
    }

    public void setCancelo(boolean cancelo) {
        this.cancelo = cancelo;
    }
}
