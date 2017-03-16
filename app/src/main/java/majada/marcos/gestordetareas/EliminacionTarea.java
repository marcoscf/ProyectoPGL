package majada.marcos.gestordetareas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Esta clase permite eliminar una tarea al seleccionar una fila del ListView.
 */

public class EliminacionTarea extends AppCompatActivity {
    ListView lv1 = null;
    AdaptadorListView adaptador = null;
    ArrayList<Fila> tareas = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar);
        //Permite ver la informacion de la fila seleccionada.
        int id = getIntent().getExtras().getInt("ID");
        AdministradorSQLite admin = new AdministradorSQLite(this, "TareasBD", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        tareas = new ArrayList<>();
        Cursor fila = bd.rawQuery("select id, nombre, estado, prioridad, fecha, hora from tareas where id =" + id, null);
        if (fila.moveToFirst()) {
            do {
                //Rellenamos el constructor de la clase Fila con los datos de la BD y lo añadimos al arrayList
                Fila tarea = new Fila(fila.getInt(0), fila.getString(1), fila.getString(2),
                        fila.getString(3), fila.getString(4), fila.getString(5));
                tareas.add(tarea);
            } while (fila.moveToNext());
        }
        lv1 = (ListView) findViewById(R.id.lista2);
        adaptador = new AdaptadorListView(this, tareas);
        lv1.setAdapter(adaptador);
        fila.close();
        bd.close();

    }

    public void Atras(View v) {
        finish();
    }

    public void Borrar(View v) {
        AdministradorSQLite admin = new AdministradorSQLite(this, "TareasBD", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        int id = getIntent().getExtras().getInt("ID");
        bd.delete("tareas", "id = " + id, null);
        bd.close();
        finish();
    }
}
