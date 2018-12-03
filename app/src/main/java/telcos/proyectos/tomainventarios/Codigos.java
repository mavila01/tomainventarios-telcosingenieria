package telcos.proyectos.tomainventarios;

public class Codigos {
    private String mCod;
    private String mDesc;
    private int mCant;

    public int getmCant() {
        return mCant;
    }

    public void setmCant(int mCant) {
        this.mCant = mCant;
    }


    public Codigos(String codigo,String descripcion,int mCant) {
        mCod = codigo;
        mDesc = descripcion;
        this.mCant = mCant;
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
                '}';
        System.out.printf(salida);
        return salida;
    }
}
