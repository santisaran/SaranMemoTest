package Juego;

/**
 * Created by saran on 17/06/17.
 */

public class Ficha {


    private boolean visible;
    private boolean tocada;
    private int imgID;

    public Ficha( int imgID){
        this.setImgID(imgID);
    }



    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isTocada() {
        return tocada;
    }

    public void setTocada(boolean tocada) {
        this.tocada = tocada;
    }

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }
}
