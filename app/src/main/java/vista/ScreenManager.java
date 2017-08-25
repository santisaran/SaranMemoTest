package vista;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class ScreenManager implements Handler.Callback, DialogInterface.OnClickListener{
    private Tablero tab;
    private AdapterTablero adapterTab;
    private PresentacionActivity activity;
    private RecyclerView list;
    private GridLayoutManager layoutManager;
    private GridLayoutManager layoutManagerMarcador;
    private static final String FORMAT = "%02d:%02d";
    private CountDownTimer cdown;
    private AlertDialog ad;
    TextView text1;
    private Handler delayHandler;
    private long tiempoActual = 0;

    public ScreenManager(PresentacionActivity a) {
        this.activity = a;
        delayHandler = new Handler(this);
        setTab(new Tablero(6,this,1));
        list = (RecyclerView) a.findViewById(R.id.list);
        layoutManager = new GridLayoutManager(a, 4);

        list.setLayoutManager(layoutManager);
        adapterTab = new AdapterTablero(getTab().getListaFichas(), getTab());
        list.setAdapter(adapterTab);

        text1 = (TextView) a.findViewById(R.id.tiempoRestanteText);
        text1.setFocusable(false);
        text1.setEnabled(false);
        text1.setCursorVisible(false);
        text1.setKeyListener(null);
        cdown = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
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
        DemoraThread dt = new DemoraThread(delayHandler,(4-tab.getDificultad())*1000,null);
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
        int dif = tab.getDificultad();
        Tablero resTab;
        resTab = getTab();
        resTab.setDificultad(dif);
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

    public AlertDialog mostrarRanking() {

        SQLiteDatabase db = activity.getDb();
        PartidasDAO pd = new PartidasDAO(db);
        for (Partidas partida: pd.getAll()
             ) {
            if (partida != null) {
                Log.d("partida", "Nombre:"+partida.getNombreJugador()+" Vidas: "+ partida.getVidas().toString()+" Tiempo: "+partida.getTiempo().toString());
            } else {
                Log.d("partida", "no recibió nada!");
            }
        }
        LayoutInflater li = LayoutInflater.from(activity);
        View viewAlert = li.inflate(R.layout.layout_dialogo_marca,null);
        TextView tvTiempo = (TextView)viewAlert.findViewById(R.id.tvMarcadorTiempoValue);
        TextView tvVidas = (TextView)viewAlert.findViewById(R.id.tvMarcadorVidasValue);
        tvTiempo.setText("" + String.format(FORMAT,
                TimeUnit.MILLISECONDS.toMinutes(tiempoActual) - TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(tiempoActual)),
                TimeUnit.MILLISECONDS.toSeconds(tiempoActual) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(tiempoActual))));
        if(tab!=null) {
            tvVidas.setText(Integer.toString(tab.getFallas()));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Ingrese su nombre");
        Log.d("marca","mostrar ranking");
        builder.setView(viewAlert);
        builder.setPositiveButton("Aceptar", this);
        builder.setNegativeButton("Cancelar", this);
        ad = builder.create();
        return ad;


    }

    public AlertDialog saveMarkOnTablero(Partidas partida){

        LayoutInflater li = LayoutInflater.from(activity);
        View viewAlert = li.inflate(R.layout.layout_dialogo_marca,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Marca");
        Log.d("marca","mostrar ranking");
        builder.setView(viewAlert);
        builder.setPositiveButton("Aceptar", this);
        builder.setNegativeButton("Cancelar", this);
        ad = builder.create();
        return ad;

    }

    public void mostrarCoincidencia(){
        Context context = activity.getApplicationContext();
        CharSequence text = activity.getString(R.string.coincid);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * This method will be invoked when a button in the dialog is clicked.
     *
     * @param dialog The dialog that received the click.
     * @param which  The button that was clicked (e.g.
     *               {@link DialogInterface#BUTTON1}) or the position
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {
        //Obtener el texto escrito por el usuario y guardarlo en la base de datos.
        if(which == DialogInterface.BUTTON_POSITIVE) {
            Log.d("User In", "Botón aceptar");
            Dialog dialogObj =Dialog.class.cast(dialog);
            EditText nombreUsusario = (EditText) dialogObj.findViewById(R.id.editTextMarcadorNombre);
            Log.d("User In", "" + nombreUsusario.getText());

        }
    }
}
