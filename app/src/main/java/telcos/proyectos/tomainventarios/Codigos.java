package telcos.proyectos.tomainventarios;

public class Codigos {
    private String mCod;
    private String mDesc;

    public Codigos(String codigo,String descripcion) {
        mCod = codigo;
        mDesc = descripcion;
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
        return "Codigos{" +
                "mCod='" + mCod + '\'' +
                ", mDesc='" + mDesc + '\'' +
                '}';
    }
}
