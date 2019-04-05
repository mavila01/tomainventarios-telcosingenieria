package telcos.proyectos.tomainventarios;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import static telcos.proyectos.tomainventarios.MenuInventarioFragment.codigoinv;
import static telcos.proyectos.tomainventarios.MenuInventarioFragment.nameBodega;
import static telcos.proyectos.tomainventarios.MenuInventarioFragment.nameEstado;
import static telcos.proyectos.tomainventarios.config.INSERT_INVENT;
import static telcos.proyectos.tomainventarios.utilidades.ClienteWeb;


public class searchMaterial extends Fragment
        implements SearchView.OnQueryTextListener {

    public searchMaterial() {
        // Required empty public constructor
    }

    private SearchView mSearchView;
    private ListView mListView;
    private EditText eBodega;
    ProgressDialog progressDialog;

    ObtenerWebService hiloconexion;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    // private final String[] mStrings = { "Google", "Apple", "Samsung", "Sony", "LG", "HTC", "Google", "Google", "Google", "Google", "Google" };
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_material,container,false);

        mSearchView = (SearchView) view.findViewById(R.id.search_view);
        mListView = (ListView) view.findViewById(R.id.list_view);
        eBodega = (EditText) view.findViewById(R.id.editTextBodega);

        progressDialog = new ProgressDialog(getActivity());

        MaterialAdapter mListAdapter = new MaterialAdapter(getActivity(),
                CodigosRepository.getInstance().getCodigos());

        eBodega.setText((CharSequence) nameBodega);

      /*  ArrayAdapter<Codigos> mListAdapter = new MaterialAdapter(getActivity(),
                CodigosRepository.getInstance().getCodigos());

        ArrayAdapter mListAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mStrings);*/


        mListView.setAdapter(mListAdapter);

        mListView.setTextFilterEnabled(true);
        setupSearchView();
        setListViewHeightBasedOnChildren(mListView);

        int count = mListView.getAdapter().getCount();
        for (int i = 0; i < count; i++) {

            Object item = mListView.getItemAtPosition(i);
            mListView.getAdapter().getItem(i);
            for (int j = 0; j < count; j++) {

            }


        }



        return view;
    }

    //ScrollView
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i,view,listView);
            if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(desiredWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth,View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    //Busqueda
    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Buscar");
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (TextUtils.isEmpty(newText)) {
            mListView.clearTextFilter();
        } else {
            mListView.setFilterText(newText);
        }
        return true;
    }

    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        inflater.inflate(R.menu.main,menu);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

//        String message = "You click fragment ";

        if (itemId == R.id.guardar_digitacion) {

            int count = mListView.getAdapter().getCount();
            MaterialAdapter ma = (MaterialAdapter) mListView.getAdapter();

            //ArrayList<Codigos> items = ma.getOriginal();

            for (int i = 0; i < count; i++) {
                Codigos a = (Codigos) mListView.getItemAtPosition(i);

                if(!a.getmCant().equals("")) {

                    hiloconexion = new ObtenerWebService();
                    hiloconexion.execute(INSERT_INVENT,"1",
                            codigoinv.getText().toString(),
                            nameBodega.toString(),
                            nameBodega.toString(), //descripcion bodega
                            a.getmCod(),
                            a.getmDesc(),
                            a.getmSerial(),
                            String.valueOf(a.getmCant()),
                            nameEstado.toString(),
                            nameBodega.toString(),//Id estado
                            "Usuario de prueba"
                    );
                }
            }
//            message += "Search menu";
        } else if (itemId == R.id.cancelar_digitacion) {
            //this.getActivity().finish();
            Fragment fragment = new searchMaterial();
            removeFragment(fragment);
//            message += "News menu";
        }

/*        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setMessage(message);
        alertDialog.show();
*/
        return super.onOptionsItemSelected(item);
    }

    private void removeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    public class ObtenerWebService extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Registrando");
            progressDialog.setMessage("Cargando... Espere un momento");
            progressDialog.setCancelable(false);
            progressDialog.show();
           //super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            Toast to = Toast.makeText(getActivity(),s,Toast.LENGTH_LONG);
            to.show();
            progressDialog.dismiss();
            //super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            String devuelve = "";
            if (params[1] == "1") {  //Insert

                try {

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("CODIGO_INVENTARIO",params[2]);
                    jsonParam.put("NAME_BODEGA",params[3]);
                    jsonParam.put("DESCRIPCION_BODEGA",params[4]);
                    jsonParam.put("CODIGO_MATERIAL",params[5]);
                    jsonParam.put("DESCRIPCION_MATERIAL",params[6]);
                    jsonParam.put("SERIAL",params[7]);
                    jsonParam.put("CANTIDAD",params[8]);
                    jsonParam.put("ESTADO_MATERIAL",params[9]);
                    jsonParam.put("ID_ESTADO_MATERIAL",params[10]);
                    jsonParam.put("USUARIO",params[11]);

                    JSONObject respuestaJSON = ClienteWeb(cadena,jsonParam);

                    int resultJSON = respuestaJSON.getInt("estado");


                    if (resultJSON == 1) {
                        devuelve = "Inventario registrado correctamente"; //Registrado Correctamente
                    } else {
                        devuelve = "Conexion fallida"; //Conexion Fallida
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return devuelve;

            }

            return null;
        }
    }


}
