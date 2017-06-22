package control;

import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by saran on 21/06/17.
 *
 * Demora los milisegundos pasados como parámetro y devuelve luego de ese tiempo, el ImageView.
 */

public class DemoraThread extends Thread {
    private Handler delayHandler;
    private int milisegundos;
    private ImageView fichaImageView;

    public DemoraThread(Handler delayHandler, int milisegundos, ImageView iv) {
        this.delayHandler = delayHandler;
        this.milisegundos = milisegundos;
        this.fichaImageView = iv;
    }

    @Override
    public void run(){
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("log","un segundo desde que se tocó ficha: ");
        Message message = new Message();
        message.obj = fichaImageView;
        delayHandler.sendMessage(message);
    }
}
