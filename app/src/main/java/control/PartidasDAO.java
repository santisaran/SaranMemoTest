package control;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.MessagePattern;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Juego.Partidas;

/**
 * Created by saran on 12/07/17.
 */

public class PartidasDAO implements DAOInterface<Partidas> {

    private SQLiteDatabase db;

    public PartidasDAO(SQLiteDatabase db){

        this.db = db;

    }

    @Override
    public long save(Partidas partida) {
        Log.d("db", "INSERT INTO jugadas" +
                "(nombre,tiempo,vidas) " +
                "VALUES('"+partida.getNombreJugador()+"','"+partida.getTiempo()+
                "',"+partida.getVidas()+")");
        db.execSQL("INSERT INTO jugadas" +
                "(nombre,tiempo,vidas) " +
                "VALUES('"+partida.getNombreJugador()+"','"+partida.getTiempo()+
                "',"+partida.getVidas()+")");
        return 0;
    }

    @Override
    public void update(Partidas type) {

    }

    @Override
    public void delete(Partidas type) {

    }

    @Override
    public Partidas get(long id) {
        Partidas partida = null;
        Cursor c;
        c = db.rawQuery("SELECT _id,nombre,tiempo,vidas" +
                " FROM jugadas WHERE _id="+id,null);
        if(c.moveToFirst())
        {
            partida = new Partidas();
            partida.setNombreJugador(c.getString(1));
            partida.setTiempo(c.getLong(2));
            partida.setVidas(c.getInt(3));

        }
        if(!c.isClosed())
        {
            c.close();
        }
        return partida;
        }

    @Override
    public ArrayList<Partidas> getAll() {
        ArrayList<Partidas> lista_partidas = new ArrayList<Partidas>();

        Cursor c;
        c = db.rawQuery("SELECT nombre,tiempo,vidas" +
                " FROM jugadas",null);

        if(c.moveToFirst())
        {
            do {
                if(c != null) {
                    Partidas partida = new Partidas();
                    partida.setNombreJugador(c.getString(0));
                    partida.setTiempo(c.getLong(1));
                    partida.setVidas(c.getInt(2));
                    lista_partidas.add (partida);
                }
            }while(c.moveToNext());
        }
        if(!c.isClosed())
        {
            c.close();
        }
        return lista_partidas;
    }

    @Override
    public int getItemCount() {
        int cuenta = 0;
        Cursor c;
        c = db.rawQuery("SELECT Count(*) FROM jugadas order by tiempo",null);
        if(c.moveToFirst()) {
            cuenta = c.getInt(0);
        }
        return cuenta;
    }
}
