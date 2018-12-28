package telcos.proyectos.tomainventarios;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.view.Menu;
import android.view.MenuItem;

public class MaterialAdapter extends ArrayAdapter<Codigos> {
    private ArrayList<Codigos> original;
    private ArrayList<Codigos> fitems;
    private Filter filter;

    public MaterialAdapter(Context context,List<Codigos> objects) {
        super(context,0,objects);
        this.original = new ArrayList<Codigos>(objects);
        this.fitems = new ArrayList<Codigos>(objects);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position,@Nullable View convertView,@NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_material,
                    parent,
                    false);
        }

        TextView codigotx = (TextView) convertView.findViewById(R.id.tv_codigo);
        TextView descripciontx = (TextView) convertView.findViewById(R.id.tv_descipcion);
        final EditText cantidadEdit = (EditText) convertView.findViewById(R.id.EditTextCantidad);
        final EditText serialEdit = (EditText) convertView.findViewById(R.id.EditTextSerial);
        final Button masSerial = (Button) convertView.findViewById(R.id.masSeriales);
        final Codigos cod = getItem(position);





        assert cod != null;

        cantidadEdit.setText(Integer.toString(cod.getmCant()));
        serialEdit.setText(cod.getmSerial());
        codigotx.setText(cod.getmCod());
        descripciontx.setText(cod.getmDesc());
        int idSerial = cod.getmIdSerial();
        if(idSerial == 0) {
            serialEdit.setVisibility(View.GONE);
            masSerial.setVisibility(View.GONE);
        }else{
            serialEdit.setVisibility(View.VISIBLE);
            masSerial.setVisibility(View.VISIBLE);
        }



        cantidadEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view,boolean b) {
                if (view != null && !b) {
                    try {
                        cod.setmCant(Integer.parseInt((cantidadEdit.getText().toString())));
                    } catch (Exception ignored) {
                    }
                }
            }
        });

        serialEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view,boolean b) {
                if (view != null && !b) {
                    try {
                        cod.setmSerial(serialEdit.getText().toString());
                    } catch (Exception ignored) {

                    }

                }
            }
        });

        masSerial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new MaterialesFilter();

        return filter;
    }

    private class MaterialesFilter extends Filter {

        MaterialesFilter() {
            super();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            String prefix = charSequence.toString().toLowerCase();

            if (prefix.length() == 0) {
                ArrayList<Codigos> list = new ArrayList<Codigos>(original);
                results.values = list;
                results.count = list.size();
            } else {
                final ArrayList<Codigos> list = new ArrayList<Codigos>(original);
                final ArrayList<Codigos> nlist = new ArrayList<Codigos>();
                int count = list.size();

                for (int i = 0; i < count; i++) {
                    final Codigos material = list.get(i);
                    final String value = material.toString().toLowerCase();

                    if (value.contains(prefix)) {
                        nlist.add(material);
                    }
                }
                results.values = nlist;
                results.count = nlist.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence,FilterResults filterResults) {
            fitems = (ArrayList<Codigos>) filterResults.values;

            clear();
            int count = fitems.size();
            for (int i = 0; i < count; i++) {
                Codigos material = (Codigos) fitems.get(i);
                add(material);
            }
        }
    }


}
