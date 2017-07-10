package control;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by saran on 21/06/17.
 *
 * Demora los milisegundos pasados como parámetro y devuelve luego de ese tiempo, el ImageView.
 */

public class DemoraSplashThread extends Thread {
    private Handler delayHandler;
    private int milisegundos;

    public DemoraSplashThread(Handler delayHandler, int milisegundos) {
        this.delayHandler = delayHandler;
        this.milisegundos = milisegundos;
    }

    @Override
    public void run(){
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("log",milisegundos+" milisegundos");
        Message message = new Message();
        message.obj = milisegundos;
        delayHandler.sendMessage(message);
    }
}
