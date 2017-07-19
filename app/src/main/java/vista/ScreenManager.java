package vista;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.saran.mimemotest.PresentacionActivity;
import com.saran.mimemotest.R;

import java.util.concurrent.TimeUnit;

import Juego.AdapterTablero;
import Juego.Partidas;
import Juego.Tablero;
import control.DemoraThread;
import control.PartidasDAO;

/**
 * Created by saran on 22/06/17.
 */

public class ScreenManager implements Handler.Callback{
    private Tablero tab;
    private AdapterTablero adapterTab;
    private PresentacionActivity activity;
    private RecyclerView list;
    private GridLayoutManager layoutManager;
    private static final String FORMAT = "%02d:%02d";
    private CountDownTimer cdown;
    TextView text1;
    private Handler delayHandler;
    private long tiempoActual = 0;

    public ScreenManager(PresentacionActivity a) {
        this.activity = a;
        delayHandler = new Handler(this);
        setTab(new Tablero(6,this));
        list = (RecyclerView) a.findViewById(R.id.list);
        layoutManager = new GridLayoutManager(a, 4);
        list.setLayoutManager(layoutManager);
        adapterTab = new AdapterTablero(getTab().getListaFichas(), getTab());
        list.setAdapter(adapterTab);
        //list.setVisibility(View.VISIBLE);

        text1 = (TextView) a.findViewById(R.id.tiempoRestanteText);
        text1.setFocusable(false);
        text1.setEnabled(false);
        text1.setCursorVisible(false);
        text1.setKeyListener(null);
        cdown = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                //adapterTab.notifyDataSetChanged();
                text1.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                tiempoActual = millisUntilFinished;
            }

            /**
             * Callback fired when the time is up.
             */
            @Override
            public void onFinish() {
                text1.setText("00:00");
            }
        };
        cdown.start();

    }

    public void mostrarFichas(){
        getTab().mostrarFichas();
        adapterTab.notifyDataSetChanged();
        DemoraThread dt = new DemoraThread(delayHandler,3000,null);
        Thread t = new Thread(dt);
        t.start();
    }

    public void endGame(){
        SQLiteDatabase db = activity.getDb();
        PartidasDAO pd = new PartidasDAO(db);
        Partidas partida = new Partidas();
        partida.setTiempo(tiempoActual);
        partida.setNombreJugador("PEPE");
        partida.setVidas(getTab().getFallas());
        pd.save(partida);
        Tablero resTab;
        resTab = getTab();
        resTab.ocultarFichas();
        resTab.shuffleFichas();
        adapterTab.notifyDataSetChanged();
        mostrarFichas();
    }

    public void restartGame(){
        Tablero resTab;
        resTab = getTab();
        resTab.ocultarFichas();
        resTab.shuffleFichas();
        adapterTab.notifyDataSetChanged();
        mostrarFichas();
    }

    @Override
    public boolean handleMessage(Message msg) {
        getTab().ocultarFichas();
        adapterTab.notifyDataSetChanged();
        cdown.cancel();
        cdown.start();
        return false;
    }

    public Tablero getTab() {
        return tab;
    }

    public void setTab(Tablero tab) {
        this.tab = tab;
    }
}