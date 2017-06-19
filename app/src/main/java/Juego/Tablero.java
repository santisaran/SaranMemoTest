package Juego;

import java.util.ArrayList;

/**
 * Created by saran on 17/06/17.
 */

public class Tablero {
    private ArrayList<Ficha> listaFichas;

    public Tablero(ArrayList<Ficha> fichas){
        setListaFichas(fichas);
    }

    public void addFicha(Ficha ficha) {
        getListaFichas().add(ficha);
    }

    public ArrayList<Ficha> getListaFichas() {
        return listaFichas;
    }

    public void setListaFichas(ArrayList<Ficha> listaFichas) {
        this.listaFichas = listaFichas;
    }
}
