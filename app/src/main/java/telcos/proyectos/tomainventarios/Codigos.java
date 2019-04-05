package telcos.proyectos.tomainventarios;

import android.support.annotation.NonNull;

public class Codigos implements Comparable<Codigos> {
    private String mCod;
    private String mDesc;
    private String mCant;
    private String mSerial;
    private int mIdSerial;

    public String getmCant() {
        return mCant;
    }

    public void setmCant(String mCant) {
        this.mCant = mCant;
    }


    public Codigos(String codigo,String descripcion,String mCant,String mSerial,int mIdSerial) {
        mCod = codigo;
        mDesc = descripcion;
        this.mCant = mCant;
        this.mSerial = mSerial;
        this.mIdSerial = mIdSerial;
    }

    public String getmCod() {
        return mCod;
    }

    public void setmCod(String mCod) {
        this.mCod = mCod;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    @Override
    public String toString() {
        String salida = "Codigos{" +
                "mCod=' " + mCod + " \'" +
                ", mDesc=' " + mDesc + " \'" +
                ", mSerial=' "+ mSerial + "\'"+
                ", mIdSerial=' "+ mIdSerial + "\'"+
                '}';
        //System.out.printf(salida);
        return salida;
    }

    public String getmSerial() {
        return mSerial;
    }

    public void setmSerial(String mSerial) {
        this.mSerial = mSerial;
    }

    public int getmIdSerial() {
        return mIdSerial;
    }

    public void setmIdSerial(int mIdSerial) {
        this.mIdSerial = mIdSerial;
    }

    @Override
    public int compareTo(@NonNull Codigos o) {

        return this.mDesc.compareTo(o.mDesc);
    }
}
