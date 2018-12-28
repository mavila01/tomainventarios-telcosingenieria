package telcos.proyectos.tomainventarios;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static telcos.proyectos.tomainventarios.utilidades.ClienteWeb;

public class CodigosRepository {


    private static CodigosRepository repository = new CodigosRepository();
    private static HashMap<String, Codigos> codis = new HashMap<>();

    public static CodigosRepository getInstance() {
        return repository;
    }

    private CodigosRepository() {

       /*
        saveCodigos(new Codigos("1024468","CONECTOR LOOKING",0));
        saveCodigos(new Codigos("100023774","MOSQUETON",0));*/
    }

    private static void saveCodigos(Codigos codes) {
        codis.put(codes.getmCod(),codes);
    }

    public List<Codigos> getCodigos() {
        return new ArrayList<>(codis.values());
    }


    public static class ObtenerMateriales extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {
            String cadena = params[0];
            String codigo;
            String descripcion;
            String serial;

            JSONObject respuestaJSON = ClienteWeb(cadena,null);
            try {

                int resultJSON = respuestaJSON.getInt("estado");
                if (resultJSON == 1) {
                    JSONArray codigoJSON = respuestaJSON.getJSONArray("materiales");
                    for (int i = 0; i < codigoJSON.length(); i++) {
                        codigo = codigoJSON.getJSONObject(i).getString("CODIGO");
                        descripcion = codigoJSON.getJSONObject(i).getString("DESCRIPCION");
                        serial = codigoJSON.getJSONObject(i).getString("REQSERIAL");

                       if(serial.equals("1")) {
                           saveCodigos(new Codigos(codigo,descripcion,0,null,1));
                        }else {
                           saveCodigos(new Codigos(codigo,descripcion,0,null,0));
                       }
                    }
                }

            } catch (
                    JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
