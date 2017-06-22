package Juego;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.saran.mimemotest.R;

/**
 * Created by saran on 22/06/17.
 */

public class ScreenManager {
    private Tablero tab;
    private AdapterTablero adapterTab;
    private Activity activity;

    public ScreenManager(Activity a){
        this.activity = a;
        tab = new Tablero(6);
        RecyclerView list = (RecyclerView)a.findViewById(R.id.list);
        GridLayoutManager layoutManager = new GridLayoutManager(a,4);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        adapterTab = new AdapterTablero(tab.getListaFichas(), tab);
        list.setAdapter(adapterTab);
        list.setVisibility(View.VISIBLE);
        tab.mostrarFichas();
    }

    public void mostrarFichas(){
        adapterTab.notifyDataSetChanged();
    }
}
