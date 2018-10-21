package telcos.proyectos.tomainventarios;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;


public class MenuInventarioFragment extends Fragment{


    public Button consulta;
    public Button digitar;
    public LinearLayout LayBodega;
    public LinearLayout LayEstadoMat;
    public Spinner spEstado;
    public Spinner spBodega;



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

        consulta = (Button)view.findViewById(R.id.buttonConsultInve);
        digitar = (Button)view.findViewById(R.id.buttonInventario);
        LayBodega = (LinearLayout)view.findViewById(R.id.LayBodega);
        LayEstadoMat = (LinearLayout)view.findViewById(R.id.LayEstadoMat);
        spEstado = (Spinner)view.findViewById(R.id.spinnerEstadoMat) ;
        spBodega = (Spinner)view.findViewById(R.id.spinnerBodega) ;



        String[] estadolist = {"Disponible", "Recogido"};
        ArrayAdapter<String> adapterEstado = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, estadolist);
        adapterEstado.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spEstado.setAdapter(adapterEstado);

        String[] bodegalist = {"AVILA BELTRAN MARCELA", "CARDENAS SUAREZ JOHN EDDISON", "CORTES BOCANEGRA CARLOS"};
        ArrayAdapter<String> adapterBodega = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, bodegalist);
        adapterBodega.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spBodega.setAdapter(adapterBodega);

        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_placeholder, fragment);
        fragmentTransaction.commit();
    }
}
