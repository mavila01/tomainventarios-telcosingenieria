package telcos.proyectos.tomainventarios;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static telcos.proyectos.tomainventarios.utilidades.ClienteWeb;

public class CodigosRepository {

    String IP = "http://localhost";
    String PUERTO_HOST = "";
    String CARPETA = "/Toma_Inventario/app";
    //Rutas web services

    String GET_MATERIALES = IP + PUERTO_HOST + CARPETA + "/obtener_materiales.php";

    ObtenerWebService hiloconexion;

    private static CodigosRepository repository = new CodigosRepository();
    private HashMap<String, Codigos> codis = new HashMap<>();

    public static CodigosRepository getInstance() {
        return repository;
    }

    private CodigosRepository() {
        saveCodigos(new Codigos("1024468", "CONECTOR LOOKING",0));
        saveCodigos(new Codigos("1036544", "CONECTOR DE CONTINUIDAD",0));
        saveCodigos(new Codigos("4025698", "DECODIFICADOR DE CLARO",0));
        saveCodigos(new Codigos("1026200", "ATENUADOR MEZCLADOR",0));
        saveCodigos(new Codigos("1025815", "ACOPLADOR DIRECCIONAL",0));
        saveCodigos(new Codigos("10003282", "ADAPTADOR - TORQUE 7/16",0));
        saveCodigos(new Codigos("1000328", "ADAPTADOR - TORQUE 5/16",0));
        saveCodigos(new Codigos("10003286", "ADAPTADOR - TORQUE 7/13",0));
        saveCodigos(new Codigos("10003232", "ADAPTADOR TORQUE 7/16",0));
        saveCodigos(new Codigos("100032", "ADAPTADOR TORQUE 16",0));
        saveCodigos(new Codigos("103282", "ADAPTADOR - TORQUE 6/16",0));
        saveCodigos(new Codigos("1000234", "MOSQUETON",0));
        saveCodigos(new Codigos("1000235", "MOSQUETON",0));
        saveCodigos(new Codigos("1000236", "MOSQUETON",0));
        saveCodigos(new Codigos("100023447", "MOSQUETON",0));
        saveCodigos(new Codigos("10002377", "MOSQUETON",0));
        saveCodigos(new Codigos("100023774", "MOSQUETON",0));
    }

    private void saveCodigos(Codigos codes) {
        codis.put(codes.getmCod(), codes);
    }

    public List<Codigos> getCodigos() {
        return new ArrayList<>(codis.values());
    }


    public class ObtenerWebService extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            String cadena = params[0];
            URL url = null;
            String muestra = "";
            try {
                JSONObject respuestaJSON = ClienteWeb(cadena,null);
                int resultJSON = respuestaJSON.getInt("estado");
                if (resultJSON == 1) {
                    JSONArray colorJSON = respuestaJSON.getJSONArray("color");
                    for (int i = 0; i < colorJSON.length(); i++) {
                        muestra = colorJSON.getJSONObject(i).getString("co_descripcion");

                    }
                }
            } catch (
                    JSONException e) {
                e.printStackTrace();
            }

           return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
