package majada.marcos.gestordetareas;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * La clase principal muestra todas las tareas en un ListView.
 */

public class MainActivity extends AppCompatActivity {
    ListView lv1 = null;
    AdaptadorListView adaptador = null;
    ArrayList<Fila> tareas = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Al iniciar la aplicacion se añade el pie al ListView.
        lv1 = (ListView) findViewById(R.id.lista);
        View pie = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer, lv1, false);
        lv1.addFooterView(pie);
    }

    @Override
    protected void onResume() {
        /**Al poner la carga de datos dentro del metodo onResume() permitimos que los cambios hechos
         * con CrearTarea, ModificacionTarea, y EliminacionTarea se muestren automaticamente al volver a la actividad principal.
         */
        super.onResume();
        AdministradorSQLite admin = new AdministradorSQLite(this, "TareasBD", null, 1);
        final SQLiteDatabase bd = admin.getReadableDatabase();
        final Cursor fila = bd.rawQuery("select id, nombre, estado, prioridad, fecha, hora from tareas", null);
        tareas = new ArrayList<>();
        if (fila.moveToFirst()) {
            do {
                //Rellenamos el constructor de la clase Fila con los datos de la BD y lo añadimos al arrayList
                Fila tarea = new Fila(fila.getInt(0), fila.getString(1), fila.getString(2),
                        fila.getString(3), fila.getString(4), fila.getString(5));
                tareas.add(tarea);
            } while (fila.moveToNext());
        }
        lv1 = (ListView) findViewById(R.id.lista);
        adaptador = new AdaptadorListView(this, tareas);
        lv1.setAdapter(adaptador);
        fila.close();
        bd.close();
    }

    public void CrearTarea(View v) {
        Intent i = new Intent(this, NuevaTarea.class);
        startActivity(i);
    }
}
