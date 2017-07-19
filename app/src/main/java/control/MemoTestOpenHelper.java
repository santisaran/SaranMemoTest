package control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by saran on 11/07/17.
 */

public class MemoTestOpenHelper extends SQLiteOpenHelper {

    private String dbFilePath;
    private String dbFileName;
    private Context context;
    private static final int DB_VERSION = 1;

    public MemoTestOpenHelper(Context context, String name) {
        super(context, name, null, DB_VERSION);
        //dbFilePath="/data/data/"+context.getPackageName()+"/databases/";
        dbFilePath = context.getDatabasePath(name).getAbsolutePath();
        dbFilePath = dbFilePath.substring(0, dbFilePath.lastIndexOf("/") + 1);
        dbFileName = name;
        this.context = context;
        checkDataBase(); // si no existe la db, la crea

    }

    private void checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = dbFilePath + dbFileName;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
//la database todavia no existe.
            try {
                Log.d("db", "copiando de assets a databases");
                copyDataBase();
            } catch (IOException e2) {
                Log.d("db", "excepcion del copy");
            }
        }
        if (checkDB != null) {
            checkDB.close();
        }
    }

    void copyDataBase() throws IOException {
        File f = new File(dbFilePath);
        f.mkdirs(); // creamos la carpeta "databases" si no existe
//creamos un input stream con el archivo desde assets
        InputStream myInput = context.getAssets().open(dbFileName);
// creamos el path del archivo de destino
        String outFileName = dbFilePath + dbFileName;
// creamos un output stream a ese path
        OutputStream myOutput = new FileOutputStream(outFileName);
//copiamos los bytes de inputfile a outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
            Log.d("db", "escribiendo " + length + " bytes");
        }
//Cerramos los streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

        /**
         * Called when the database is created for the first time. This is where the
         * creation of tables and the initial population of the tables should happen.
         *
         * @param db The database.
         */
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
