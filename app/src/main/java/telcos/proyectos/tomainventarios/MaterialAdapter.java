package telcos.proyectos.tomainventarios;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaterialAdapter extends ArrayAdapter<Codigos> {
    private ArrayList<Codigos> original;
    private ArrayList<Codigos> fitems;
    private Filter filter;
    // private int idEditxt= 0;

    public MaterialAdapter(Context context,List<Codigos> objects) {
        super(context,0,objects);
        this.original = new ArrayList<Codigos>(objects);
        this.fitems = new ArrayList<Codigos>(objects);

    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(final int position,@Nullable View convertView,@NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_material,
                    parent,
                    false);
        }
        Collections.sort(original);
        TextView codigotx = (TextView) convertView.findViewById(R.id.tv_codigo);
        TextView descripciontx = (TextView) convertView.findViewById(R.id.tv_descipcion);
        Button cmd_agregarItemSerializado = (Button) convertView.findViewById(R.id.cmd_agregarItemSerializado);
        final EditText serialEdit = (EditText) convertView.findViewById(R.id.serialEdit);
        final EditText cantidadEdit = (EditText) convertView.findViewById(R.id.EditTextCantidad);
        //final TextView textSerial = (TextView) convertView.findViewById(R.id.textSerial);
        final Codigos cod = getItem(position);

        assert cod != null;

        cmd_agregarItemSerializado.setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //this.original.add(position+1,cod);

                        try {
                            MaterialAdapter ma = ((MaterialAdapter) ((ListView) view.getParent().getParent()).getAdapter());
                            Codigos duplicado = new Codigos(cod.getmCod(),cod.getmDesc(),"",null,cod.getmIdSerial());
                            ma.getOriginal().add(position + 1,duplicado);
                            ma.clear();
                            ma.addAll(ma.getOriginal());
                            notifyDataSetChanged();
                        } finally {

                        }
                    }
                });


        cantidadEdit.setText(cod.getmCant());
        serialEdit.setText(cod.getmSerial());
        codigotx.setText(cod.getmCod());
        descripciontx.setText(cod.getmDesc());

        int idSerial = cod.getmIdSerial();
        if (idSerial == 0) {
            serialEdit.setVisibility(View.GONE);
            cmd_agregarItemSerializado.setVisibility(View.GONE);
        } else {
            serialEdit.setVisibility(View.VISIBLE);
            //textSerial.setText("Serializado");
            cmd_agregarItemSerializado.setVisibility(View.VISIBLE);
        }

        cantidadEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view,boolean b) {
                if (view != null && !b) {
                    try {
                        cod.setmCant((cantidadEdit.getText().toString()));
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

        return convertView;
    }

    @Nullable
    @Override
    public Codigos getItem(int position) {
        return super.getItem(position);
    }

    //Agregar un Layout y Editext para serializado
   /* public void crearText(RelativeLayout relativeLayout) {

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setBackgroundColor(0xff99ccff);
        relativeLayout.addView(linearLayout);

        EditText editText1 = new EditText(getContext());
        editText1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editText1.setText("TextView"+idEditxt);
        //editText1.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
        //editText1.setPadding(20,20,20,20);// in pixels (left, top, right, bottom)
        linearLayout.addView(editText1);

    }
*/
    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new MaterialesFilter();

        return filter;
    }

    public ArrayList<Codigos> getOriginal() {
        return original;
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
                ArrayList<Codigos> list = new ArrayList<Codigos>(getOriginal());
                results.values = list;
                results.count = list.size();
            } else {
                final ArrayList<Codigos> list = new ArrayList<Codigos>(getOriginal());
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
