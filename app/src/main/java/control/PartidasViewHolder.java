package control;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.saran.mimemotest.R;

import org.w3c.dom.Text;

/**
 * Created by saran on 27/07/17.
 */

public class PartidasViewHolder extends RecyclerView.ViewHolder {

    TextView tvNombre;
    TextView tvTiempo;
    TextView tvVidas;

    public PartidasViewHolder(View itemView) {
        super(itemView);
        tvNombre = (TextView)itemView.findViewById(R.id.textViewTableroNombre);
        tvTiempo = (TextView)itemView.findViewById(R.id.textViewTableroTiempo);
        tvVidas = (TextView)itemView.findViewById(R.id.textViewTableroVidas);

    }
}
