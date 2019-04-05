package telcos.proyectos.tomainventarios;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static telcos.proyectos.tomainventarios.config.*;
import static telcos.proyectos.tomainventarios.utilidades.ClienteWeb;


public class MenuInventarioFragment extends Fragment {

    public static EditText codigoinv;
    public Button consulta;
    public Button digitar;
    public LinearLayout LayBodega;
    public LinearLayout LayEstadoMat;
    @SuppressLint("StaticFieldLeak")
    public static Spinner spEstado;
    public static Spinner spBodega;
    public static Object nameBodega;
    public static Object nameEstado;
    String estadoItem = "";
    String nodoItem = "";

    public static ArrayList<String> estadolist;
    public static ArrayList<String> nodoslist;

    public ObtenerWebService hiloconexion;
    public ObtenerWebService hiloconexion2;
    public ObtenerInventario hiloconexion3;

    ProgressDialog progressDialog;

    public MenuInventarioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        estadolist = new ArrayList<String>();
        nodoslist = new ArrayList<String>();



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

        //serialEdit = (EditText) view.findViewById(R.id.EditTextSerial);

        progressDialog = new ProgressDialog(getActivity());

//        serialEdit.setVisibility(View.GONE);

        spBodega.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemSelected(AdapterView<?> parent,View view,int position,long id) {

                nameBodega = parent.getItemAtPosition(position);
                /*Toast.makeText(getActivity(), nameBodega.toString(),
                        Toast.LENGTH_SHORT).show();*/

                nodoItem = Objects.toString(id + 1,null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemSelected(AdapterView<?> parent,View view,int position,long id) {

                //int p = position;
                nameEstado = parent.getItemAtPosition(position);
                estadoItem = Objects.toString(id + 1,null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiloconexion3 = new ObtenerInventario();
                String cadenallamada4 = GET_PREINVENTARIO + "?codigo=" + codigoinv.getText().toString();
                hiloconexion3.execute(cadenallamada4,"1");
            }
        });

        digitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onPause();
                Fragment fragment = null;
                fragment = new searchMaterial();
                replaceFragment(fragment);

            }
        });


        return view;
    }

    @SuppressLint("StaticFieldLeak")
    public class ObtenerWebService extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String cadena = params[0];
            String muestra;
            String estado;

            if (params[1].equals("1")) {
                try {
                    JSONObject respuestaJSON = ClienteWeb(cadena,null);


                    int resultJSON = respuestaJSON.getInt("estado");
                    if (resultJSON == 1) {
                        JSONArray estadoJSON = respuestaJSON.getJSONArray("descripcion");
                        for (int i = 0; i < estadoJSON.length(); i++) {
                            muestra = estadoJSON.getJSONObject(i).getString("ESTADOMATERIALDSC");
                            estadolist.add(muestra);
                        }
                    }

                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }
            } else if (params[1].equals("2")) {

                try {
                    JSONObject respuestaJSON = ClienteWeb(cadena,null);


                    int resultJSON = respuestaJSON.getInt("estado");
                    if (resultJSON == 1) {
                        JSONArray nombreJSON = respuestaJSON.getJSONArray("descripcion");
                        for (int i = 0; i < nombreJSON.length(); i++) {
                            muestra = nombreJSON.getJSONObject(i).getString("Caption");
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
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public class ObtenerInventario extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            if (s.equals("No se obtuvo registro")){
                alertDialog.setTitle("Alerta!");
                alertDialog.setMessage(s);
                alertDialog.show();
            }else if(s.equals("1")){

                progressDialog.setTitle("Consultando");
                progressDialog.setMessage("Cargando... Espere un momento");
                progressDialog.setCancelable(false);
                progressDialog.show();

                hiloconexion = new ObtenerWebService();
                String cadenallamada = GET_ESTADO + "?codigo=" + codigoinv.getText().toString();
                hiloconexion.execute(cadenallamada,"1");

                hiloconexion2 = new ObtenerWebService();
                String cadenallamada2 = GET_NODO + "?codigo=" + codigoinv.getText().toString();
                hiloconexion2.execute(cadenallamada2,"2");

                CodigosRepository.ObtenerMateriales hiloconexion3 = new CodigosRepository.ObtenerMateriales();
                String cadenallamada3 = GET_MATERIALES + "?codigo=" + codigoinv.getText().toString();
                hiloconexion3.execute(cadenallamada3);



            }else if(s.equals("0")){
                s = "El inventario no se encuentra activo";
                alertDialog.setTitle("Alerta!");
                alertDialog.setMessage(s);
                alertDialog.show();
            }else if(s.equals("Se necesita un identificador")){
                alertDialog.setTitle("Alerta!");
                alertDialog.setMessage(s);
                alertDialog.show();
            }


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {
            String cadena = params[0];
            String muestra = null;

            if (params[1] == "1") {
                try {
                    JSONObject respuestaJSON = ClienteWeb(cadena,null);


                    int resultJSON = respuestaJSON.getInt("estado");
                    if (resultJSON == 1) {
                        JSONArray estadoJSON = respuestaJSON.getJSONArray("descripcion");
                        for (int i = 0; i < estadoJSON.length(); i++) {
                            muestra = estadoJSON.getJSONObject(i).getString("PREPINVENTESTADO");
                        }
                    }else if (resultJSON == 2){
                        muestra = "No se obtuvo registro";
                    }else if (resultJSON == 3){
                        muestra = "Se necesita un identificador";
                    }

                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }
            }
            return muestra;
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
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
