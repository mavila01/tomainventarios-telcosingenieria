package telcos.proyectos.tomainventarios;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;


public class searchMaterial extends Fragment
        implements SearchView.OnQueryTextListener{

    public searchMaterial() {
        // Required empty public constructor
    }

    private SearchView mSearchView;
    private ListView mListView;

    private final String[] mStrings = { "Google", "Apple", "Samsung", "Sony", "LG", "HTC", "Google", "Google", "Google", "Google", "Google" };
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_material,container,false);

        mSearchView = (SearchView) view.findViewById(R.id.search_view);
        mListView = (ListView) view.findViewById(R.id.list_view);

        MaterialAdapter mListAdapter = new MaterialAdapter(getActivity(),
                CodigosRepository.getInstance().getCodigos());



      /*  ArrayAdapter<Codigos> mListAdapter = new MaterialAdapter(getActivity(),
                CodigosRepository.getInstance().getCodigos());

        ArrayAdapter mListAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mStrings);*/

        mListView.setAdapter(mListAdapter);

        mListView.setTextFilterEnabled(true);
        setupSearchView();

        return view;
    }



    //Busqueda
    private void setupSearchView() {
        //mSearchView.setIconifiedByDefault(false);
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
}
