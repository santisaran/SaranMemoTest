package Juego;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.saran.mimemotest.R;

/**
 * Created by saran on 19/06/17.
 */

public class TableroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private OnFichaClick listener;
    ImageView ImagenFicha;

    public TableroViewHolder(View itemView, OnFichaClick fichaListener) {
        super(itemView);
        ImagenFicha = (ImageView) itemView.findViewById(R.id.imgFichaID);
        itemView.setOnClickListener(this);
        this.listener = fichaListener;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Ficha ficha = listener.onFichaClick(this.getAdapterPosition());
        ImagenFicha.setImageResource(ficha.getImgID());
    }
}
