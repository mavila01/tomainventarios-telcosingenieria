package telcos.proyectos.tomainventarios;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static telcos.proyectos.tomainventarios.config.GET_ESTADO;
import static telcos.proyectos.tomainventarios.config.GET_NODO;
import static telcos.proyectos.tomainventarios.utilidades.ClienteWeb;


public class MenuInventarioFragment extends Fragment {


    public EditText codigoinv;
    public Button consulta;
    public Button digitar;
    public LinearLayout LayBodega;
    public LinearLayout LayEstadoMat;
    @SuppressLint("StaticFieldLeak")
    public Spinner spEstado;
    public Spinner spBodega;
    String estadoItem = "";
    String nodoItem = "";

    public static ArrayList<String> estadolist;
    public static ArrayList<String> nodoslist;

    public ObtenerWebService hiloconexion;
    public ObtenerWebService hiloconexion2;

    public MenuInventarioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_inventario,container,false);

        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        //getActivity().requestWindowFeature(Window.FEATURE_ACTION_BAR);

        codigoinv = (EditText) view.findViewById(R.id.editTextCodigoInv);
        consulta = (Button) view.findViewById(R.id.buttonConsultInve);
        digitar = (Button) view.findViewById(R.id.buttonInventario);
        LayBodega = (LinearLayout) view.findViewById(R.id.LayBodega);
        LayEstadoMat = (LinearLayout) view.findViewById(R.id.LayEstadoMat);
        spEstado = (Spinner) view.findViewById(R.id.spinnerEstadoMat);
        spBodega = (Spinner) view.findViewById(R.id.spinnerBodega);

        estadolist = new ArrayList<String>();
        nodoslist = new ArrayList<String>();

        spBodega.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemSelected(AdapterView<?> parent,View view,int position,long id) {

                estadoItem = Objects.toString(id + 1,null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemSelected(AdapterView<?> parent,View view,int position,long id) {

                nodoItem = Objects.toString(id + 1,null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiloconexion = new ObtenerWebService();
                String cadenallamada = GET_ESTADO + "?codigo=" + codigoinv.getText().toString();
                hiloconexion.execute(cadenallamada,"1");

                hiloconexion2 = new ObtenerWebService();
                String cadenallamada2 = GET_NODO + "?codigo=" + codigoinv.getText().toString();
                hiloconexion2.execute(cadenallamada2,"2");

                LayBodega.setVisibility(View.VISIBLE);
                LayEstadoMat.setVisibility(View.VISIBLE);
            }
        });

        digitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment = new searchMaterial();
                replaceFragment(fragment);
            }
        });


        return view;
    }

    public class ObtenerWebService extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String cadena = params[0];
            String muestra;

            if (params[1] == "1") {
                try {
                    JSONObject respuestaJSON = ClienteWeb(cadena,null);


                    int resultJSON = respuestaJSON.getInt("estado");
                    if (resultJSON == 1) {
                        JSONArray colorJSON = respuestaJSON.getJSONArray("descripcion");
                        for (int i = 0; i < colorJSON.length(); i++) {
                            muestra = colorJSON.getJSONObject(i).getString("ESTADOMATERIALDSC");
                            estadolist.add(muestra);
                        }
                    }

                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }
            } else if (params[1] == "2") {

                try {
                    JSONObject respuestaJSON = ClienteWeb(cadena,null);


                    int resultJSON = respuestaJSON.getInt("estado");
                    if (resultJSON == 1) {
                        JSONArray colorJSON = respuestaJSON.getJSONArray("descripcion");
                        for (int i = 0; i < colorJSON.length(); i++) {
                            muestra = colorJSON.getJSONObject(i).getString("Caption");
                            nodoslist.add(muestra);
                        }
                    }

                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }

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
            populateSpinner(spEstado,estadolist);
            populateSpinner(spBodega,nodoslist);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public void populateSpinner(Spinner spinner,ArrayList arrayList) {
        List<String> lables = new ArrayList<String>();

        for (int i = 0; i < arrayList.size(); i++) {
            lables.add((String) arrayList.get(i));
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,lables);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_placeholder,fragment);
        fragmentTransaction.commit();
    }
}
