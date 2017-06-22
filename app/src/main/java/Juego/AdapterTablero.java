package Juego;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.saran.mimemotest.R;

import java.util.ArrayList;

/**
 * Created by saran on 19/06/17.
 */

public class AdapterTablero extends RecyclerView.Adapter<TableroViewHolder>{

    private ArrayList<Ficha> listaFichas;
    private OnFichaClick fichaListener;

    public AdapterTablero(ArrayList<Ficha> fichas, OnFichaClick fichaListener){

        listaFichas = fichas;
        this.fichaListener = fichaListener;

    }

    @Override
    public TableroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ficha_layout, parent, false);
        TableroViewHolder tabViewHolder = new TableroViewHolder(v, fichaListener);
        return tabViewHolder;
    }

    @Override
    public void onBindViewHolder(TableroViewHolder holder, int position) {
        Ficha f = listaFichas.get(position);
        if(f.getTocada()){
            holder.ImagenFicha.setImageResource(f.getImgID());
        }
        else{
            holder.ImagenFicha.setImageResource(R.drawable.question_icon);
        }


    }

    @Override
    public int getItemCount() {
        return listaFichas.size();
    }

    /**
     * Vuelve a poner todos los imageView de las imágenes en question_icon.
     */
    public void resetAllImages(){
        notifyDataSetChanged();
    }
}


