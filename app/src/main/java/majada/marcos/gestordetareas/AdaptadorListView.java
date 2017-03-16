package majada.marcos.gestordetareas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Este es un adaptador de ListView personalizado, debido a que los que trae
 * Android Studio por defecto no servian para la fila que debiamos usar.
 */

class AdaptadorListView extends BaseAdapter {
    private Context context;
    private ArrayList<Fila> datos;
    private static LayoutInflater inflater = null;

    AdaptadorListView(Context context, ArrayList<Fila> datos) {
        this.context = context;
        this.datos = datos;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int position) {
        return datos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int posicion, final View contentView, final ViewGroup parent) {
        View v = contentView;
        if (v == null) {
            v = inflater.inflate(R.layout.fila, parent, false);
        }
        /*
        Obtenemos los datos de un elemento del arraylist y los volcamos
        en un objeto Fila, ademas enlazamos la fila con el contentview.
        */
        final Fila fila = datos.get(posicion);
        v.setTag(fila);
        //En los campos de la activity Fila ponemos los datos.
        TextView a1 = (TextView) v.findViewById(R.id.filaNombre);
        a1.setText(fila.getNombre());
        CheckBox a2 = (CheckBox) v.findViewById(R.id.filaEstado);
        if (fila.getEstado().equals("Terminada")) {
            a2.setChecked(true);
        }
        TextView a3 = (TextView) v.findViewById(R.id.filaPrioridad);
        a3.setText(fila.getPrioridad());
        TextView a4 = (TextView) v.findViewById(R.id.filaFecha);
        a4.setText(fila.getFecha());
        TextView a5 = (TextView) v.findViewById(R.id.filaHora);
        a5.setText(fila.getHora());
        //Al hacer clic en la fila se abrira la actividad para modificar los datos de la fila.
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                int id = fila.getId();
                String nom = fila.getNombre();
                String est = fila.getEstado();
                String pri = fila.getPrioridad();
                String fec = fila.getFecha();
                String hor = fila.getHora();
                b.putInt("ID", id);
                b.putString("NOMBRE", nom);
                b.putString("ESTADO", est);
                b.putString("PRIORIDAD", pri);
                b.putString("FECHA", fec);
                b.putString("HORA", hor);
                Intent i = new Intent(context, ModificacionTarea.class);
                i.putExtras(b);
                context.startActivity(i);
            }
        });
        //Al hacer un clic prolongado se abrira la actividad de eliminar la fila.
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Bundle b = new Bundle();
                int id = fila.getId();
                b.putInt("ID", id);
                Intent i = new Intent(context, EliminacionTarea.class);
                i.putExtras(b);
                context.startActivity(i);
                return true;
            }
        });
        return v;
    }
}
