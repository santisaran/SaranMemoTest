package Juego;

import android.app.Activity;
import android.os.CountDownTimer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.saran.mimemotest.R;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

/**
 * Created by saran on 22/06/17.
 */

public class ScreenManager {
    private Tablero tab;
    private AdapterTablero adapterTab;
    private Activity activity;
    private RecyclerView list;
    private GridLayoutManager layoutManager;
    private static final String FORMAT = "%02d:%02d";
    TextView text1;

    public ScreenManager(Activity a) {
        this.activity = a;
        tab = new Tablero(6);
        list = (RecyclerView) a.findViewById(R.id.list);
        layoutManager = new GridLayoutManager(a, 4);
        list.setLayoutManager(layoutManager);
        adapterTab = new AdapterTablero(tab.getListaFichas(), tab);
        list.setAdapter(adapterTab);
        list.setVisibility(View.VISIBLE);
        tab.mostrarFichas();
        text1 = (TextView) a.findViewById(R.id.tiempoRestanteText);
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {

                text1.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            /**
             * Callback fired when the time is up.
             */
            @Override
            public void onFinish() {
                text1.setText("done!");
            }
        }.start();
    }
    public void mostrarFichas(){
        adapterTab.notifyDataSetChanged();
    }
}
