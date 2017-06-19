package Juego;

import com.saran.mimemotest.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by saran on 17/06/17.
 */

public class Tablero {
    private ArrayList<Ficha> listaFichas;

    public Tablero (void) {
        setListaFichas(new ArrayList<Ficha>());
        addFicha(new Ficha(R.drawable.img_1));
        addFicha(new Ficha(R.drawable.img_2));
        addFicha(new Ficha(R.drawable.img_3));
        addFicha(new Ficha(R.drawable.img_4));
        addFicha(new Ficha(R.drawable.img_5));
        addFicha(new Ficha(R.drawable.img_6));
        addFicha(new Ficha(R.drawable.img_1));
        addFicha(new Ficha(R.drawable.img_2));
        addFicha(new Ficha(R.drawable.img_3));
        addFicha(new Ficha(R.drawable.img_4));
        addFicha(new Ficha(R.drawable.img_5));
        addFicha(new Ficha(R.drawable.img_6));
        shuffleFichas();
    }

    public void shuffleFichas(void){
        Collections.shuffle(listaFichas);
    }

    public void addFicha(Ficha ficha) {
        listaFichas.add(ficha);
    }

    public ArrayList<Ficha> getListaFichas() {
        return listaFichas;
    }

    public void setListaFichas(ArrayList<Ficha> listaFichas) {
        this.listaFichas = listaFichas;
    }
}
