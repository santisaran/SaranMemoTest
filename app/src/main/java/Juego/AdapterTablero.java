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

    public AdapterTablero(ArrayList<Ficha> fichas){

        listaFichas = fichas;

    }

    @Override
    public TableroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ficha_layout, parent, false);
        TableroViewHolder tabViewHolder = new TableroViewHolder(v);
        return tabViewHolder;
    }

    @Override
    public void onBindViewHolder(TableroViewHolder holder, int position) {
        Ficha f = listaFichas.get(position);
        holder.ImagenFicha.setImageResource(f.getImgID());
    }

    @Override
    public int getItemCount() {
        return listaFichas.size();
    }
}


