package Juego;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.saran.mimemotest.R;

/**
 * Created by saran on 19/06/17.
 */

public class TableroViewHolder extends RecyclerView.ViewHolder {

    ImageView ImagenFicha;

    public TableroViewHolder(View itemView) {
        super(itemView);
        ImagenFicha = (ImageView) itemView.findViewById(R.id.imgFichaID);
    }

}
