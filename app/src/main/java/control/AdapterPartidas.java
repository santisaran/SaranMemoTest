package control;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.saran.mimemotest.R;

import java.util.List;

import Juego.Partidas;
import Juego.TableroViewHolder;

/**
 * Created by saran on 27/07/17.
 */

public class AdapterPartidas extends RecyclerView.Adapter<PartidasViewHolder> {

    private int dbCount;   // cantidad de items en la base de datos.
    private PartidasDAO partidasDAO;
    private List<Partidas> partidas;

    public void setPartidas(int dbCountm, PartidasDAO partidasDAO){
        this.dbCount = dbCount;
        this.partidasDAO = partidasDAO;
        partidas = this.partidasDAO.getAll();

    }

    @Override
    public PartidasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_marcador, parent, false);
        PartidasViewHolder partidasViewHolder = new PartidasViewHolder(v);
        return partidasViewHolder;
    }

    @Override
    public void onBindViewHolder(PartidasViewHolder holder, int position) {
        Partidas partida = partidas.get(position);
        holder.tvVidas.setText(partida.getVidas());
        holder.tvTiempo.setText(partida.getTiempo().toString());
        holder.tvNombre.setText(partida.getNombreJugador());
    }

    @Override
    public int getItemCount() {
        return dbCount;
    }

}
