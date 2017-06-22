package Juego;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.saran.mimemotest.R;

import java.util.ArrayList;

import control.DemoraThread;

/**
 * Created by saran on 17/06/17.
 */


public class Tablero implements OnFichaClick, Handler.Callback{

    private Handler delayHandler;

    static final int[] imagenes = {
            R.drawable.img_1, R.drawable.img_2, R.drawable.img_3,
            R.drawable.img_4, R.drawable.img_5, R.drawable.img_6
    };
    
    private ArrayList<Ficha> listaFichas;

    public Tablero(int nroPiezas){
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
        delayHandler = new Handler(this);
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

    }

    @Override
    public Ficha onFichaClick(int position, ImageView imagenFicha) {
        DemoraThread dt = new DemoraThread(delayHandler,1000,imagenFicha);
        Thread t = new Thread(dt);
        t.start();
        Ficha fichaClicked =  listaFichas.get(position);
        fichaClicked.setTocada(true);
        Log.d("fichas","tocada ficha"+position);
        return fichaClicked;
    }

    @Override
    public boolean handleMessage(Message msg) {
        ((ImageView)msg.obj).setImageResource(R.drawable.question_icon);
        return false;
    }
}
