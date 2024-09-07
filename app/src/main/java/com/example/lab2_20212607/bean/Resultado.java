package com.example.lab2_20212607.bean;

import java.io.Serializable;

public class Resultado implements Serializable {

    private int tiempo;
    private boolean cancelo;

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public boolean isCancelo() {
        return cancelo;
    }

    public void setCancelo(boolean cancelo) {
        this.cancelo = cancelo;
    }
}
