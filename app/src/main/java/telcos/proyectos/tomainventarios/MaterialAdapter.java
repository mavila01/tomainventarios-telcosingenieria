package telcos.proyectos.tomainventarios;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MaterialAdapter extends ArrayAdapter<Codigos> {

    public MaterialAdapter(Context context,List<Codigos> objects) {
        super(context,0,objects);
    }

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

        Codigos cod = getItem(position);

        assert cod != null;
        codigotx.setText(cod.getmCod());
        descripciontx.setText(cod.getmDesc());

        return convertView;
    }
}
