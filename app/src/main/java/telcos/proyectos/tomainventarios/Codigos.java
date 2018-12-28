package telcos.proyectos.tomainventarios;

public class Codigos {
    private String mCod;
    private String mDesc;
    private int mCant;
    private String mSerial;
    private int mIdSerial;

    public int getmCant() {
        return mCant;
    }

    public void setmCant(int mCant) {
        this.mCant = mCant;
    }


    public Codigos(String codigo,String descripcion,int mCant,String mSerial,int mIdSerial) {
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
        System.out.printf(salida);
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
}
