package Juego;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.saran.mimemotest.R;

import java.util.ArrayList;
import java.util.Collections;

import control.DemoraThread;

/**
 * Created by saran on 17/06/17.
 */


public class Tablero implements OnFichaClick, Handler.Callback{

    private int contadorFichasMostradas;
    private Handler delayHandler;
    private ImageView imageViewAux;
    private Ficha fichaAux;

    static final int[] imagenes = {
            R.drawable.img_1, R.drawable.img_2, R.drawable.img_3,
            R.drawable.img_4, R.drawable.img_5, R.drawable.img_6
    };
    
    private ArrayList<Ficha> listaFichas;

    public Tablero(int nroPiezas){
        contadorFichasMostradas = 0;
        //me aseguro que no se puedan poner mas fichas de las que existen.
        if(nroPiezas>imagenes.length){
            nroPiezas = imagenes.length;
        }
        listaFichas = new ArrayList<Ficha>();
        int i=0;
        for (int img: imagenes) {
            addFicha(new Ficha(img));
            addFicha(new Ficha(img));
            i++;
            if(i>=nroPiezas){
                break;
            }
        }
        shuffleFichas();
        delayHandler = new Handler(this);
        fichaAux = null;
    }

    public void shuffleFichas(){
        Collections.shuffle(listaFichas);
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

    public void mostrarFichas(){
        for (Ficha ficha: listaFichas) {
            ficha.setTocada(true);
        }
    }

    public void ocultarFichas(){
        for (Ficha ficha: listaFichas) {
            ficha.setTocada(false);
            ficha.setMatched(false);
        }
    }

    @Override
    public void onFichaClick(int position, ImageView imagenFicha) {

        Ficha fichaClicked = null;
        fichaClicked = listaFichas.get(position);
        if(!fichaClicked.getMatched()) {
            contadorFichasMostradas++;
            if (contadorFichasMostradas < 3) {
                if (contadorFichasMostradas == 1) {
                    fichaClicked = listaFichas.get(position);
                    if (!fichaClicked.getMatched()) {
                        fichaClicked.setTocada(true);
                        imagenFicha.setImageResource(fichaClicked.getImgID());
                        imageViewAux = imagenFicha;
                        fichaAux = fichaClicked;
                    } else {
                        contadorFichasMostradas = 0;
                    }

                }
                if (contadorFichasMostradas == 2) {
                    fichaClicked = listaFichas.get(position);
                    fichaClicked.setTocada(true);
                    imagenFicha.setImageResource(fichaClicked.getImgID());
                    if (fichaClicked != fichaAux) {
                        if (fichaClicked.getImgID() == fichaAux.getImgID()) {
                            fichaClicked.setMatched(true);
                            fichaAux.setMatched(true);
                            contadorFichasMostradas = 0;
                        } else {
                            DemoraThread dt = new DemoraThread(delayHandler, 1000, imagenFicha);
                            Thread t = new Thread(dt);
                            t.start();
                        }
                    } else { //se hizo click en la misma ficha
                        contadorFichasMostradas--;
                    }
                }
                Log.d("fichas", "tocada ficha" + position);
            }
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        contadorFichasMostradas = 0;
        imageViewAux.setImageResource(R.drawable.question_icon);
        ((ImageView)msg.obj).setImageResource(R.drawable.question_icon);
        return false;
    }
}
