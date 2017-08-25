package com.saran.mimemotest;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.saran.mimemotest.dummy.DummyContent;

import control.MemoTestOpenHelper;
import vista.ScreenManager;
import control.DemoraSplashThread;

public class PresentacionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Handler.Callback{

    private int postion;
    private ScreenManager sm;
    private Handler delayHandler;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_layout);
        delayHandler = new Handler(this);
        DemoraSplashThread dt = new DemoraSplashThread(delayHandler,2000);
        Thread t = new Thread(dt);
        t.start();
        sm = null;
        MemoTestOpenHelper openHelper = new MemoTestOpenHelper(this, "memotdb.db");
        setDb(openHelper.getWritableDatabase());


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.presentacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_game) {
            if(sm == null) {
                sm = new ScreenManager(this);
            }else{
                sm.restartGame();
            }
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.nivel) + sm.getTab().getDificultad();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            // Handle the camera action
        }else if (id == R.id.nav_difficulty) {
            if(sm!=null) {


                final AlertDialog.Builder singlechoicedialog = new AlertDialog.Builder(this);
                final CharSequence[] Report_items = { "NIVEL I", "NIVEL II", "NIVEL III"};
                singlechoicedialog.setTitle(getString(R.string.selDif));
                singlechoicedialog.setSingleChoiceItems(Report_items, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                sm.getTab().setDificultad(item+1);
                                dialog.cancel();
                            }
                        });
                AlertDialog alert_dialog = singlechoicedialog.create();
                alert_dialog.show();

// set defult select value
                alert_dialog.getListView().setItemChecked(postion, true);
            }
        }else if(id == R.id.nav_ranking){
            if(sm==null) {
                sm = new ScreenManager(this);
            }
            Fragment fragment;
            fragment = new PartidasFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.contenedor    , fragment)
                    .commit();
            //sm.mostrarRanking().show();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean handleMessage(Message msg) {
        setContentView(R.layout.activity_presentacion);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        return false;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

}
