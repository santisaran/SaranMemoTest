package Juego;

/**
 * Created by saran on 17/06/17.
 */

public class Ficha {


    private boolean visible;
    private boolean tocada;
    private boolean matched;
    private int imgID;


    public Ficha( int imgID){
        this.setImgID(imgID);
        this.visible = false;
        this.tocada = false;
        this.matched = false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setTocada(boolean tocada) {
        this.tocada = tocada;
    }

    public boolean getTocada(){ return tocada;}

    public void setMatched(){
        matched = true;
    }

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }
}
