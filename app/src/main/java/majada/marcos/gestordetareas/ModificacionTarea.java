package majada.marcos.gestordetareas;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

/**
 * Esta clase permite modificar los datos de una tarea a partir de la fila seleccionada.
 */

public class ModificacionTarea extends AppCompatActivity {
    int id;
    String nombre;
    String textoEstado;
    String textoPrioridad;
    String fecha;
    String hora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_tarea);

        EditText t1 = (EditText) findViewById(R.id.et1);
        RadioGroup t2 = (RadioGroup) findViewById(R.id.rg1);
        RadioGroup t3 = (RadioGroup) findViewById(R.id.rg2);
        TextView t4 = (TextView) findViewById(R.id.fecha);
        TextView t5 = (TextView) findViewById(R.id.hora);

        id = getIntent().getExtras().getInt("ID");
        nombre = getIntent().getExtras().getString("NOMBRE");
        textoEstado = getIntent().getExtras().getString("ESTADO");
        textoPrioridad = getIntent().getExtras().getString("PRIORIDAD");
        fecha = getIntent().getExtras().getString("FECHA");
        hora = getIntent().getExtras().getString("HORA");

        t1.setText(nombre);
        if (textoEstado.equals("Terminada")) {
            t2.check(R.id.rb1);
        }
        if (textoPrioridad.equals("Baja")) {
            t3.check(R.id.rb3);
        } else if (textoPrioridad.equals("Alta")) {
            t3.check(R.id.rb5);
        }
        t4.setText(fecha);
        t5.setText(hora);
    }

    public void Atras(View v) {
        finish();
    }

    public void Modificar(View v) {
        EditText t1 = (EditText) findViewById(R.id.et1);
        RadioGroup t2 = (RadioGroup) findViewById(R.id.rg1);
        RadioGroup t3 = (RadioGroup) findViewById(R.id.rg2);
        TextView t4 = (TextView) findViewById(R.id.fecha);
        TextView t5 = (TextView) findViewById(R.id.hora);
        RadioButton estado = (RadioButton) t2.getChildAt(t2.indexOfChild(findViewById(t2.getCheckedRadioButtonId())));
        RadioButton prioridad = (RadioButton) t3.getChildAt(t3.indexOfChild(findViewById(t3.getCheckedRadioButtonId())));
        //Obtenemos el contenido de los campos en formato String.
        String nombre = t1.getText().toString();
        //Buscamos el radioButton seleccionado y obtenemos el texto que contiene.
        String textoEstado = (String) estado.getText();
        String textoPrioridad = (String) prioridad.getText();
        String fecha = t4.getText().toString();
        String hora = t5.getText().toString();

        AdministradorSQLite admin = new AdministradorSQLite(this, "TareasBD", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("estado", textoEstado);
        registro.put("prioridad", textoPrioridad);
        registro.put("fecha", fecha);
        registro.put("hora", hora);
        bd.update("tareas", registro, "id = " + id, null);
        bd.close();
        finish();
    }

    public void MostrarCalendario(View v) {
        Intent i = new Intent(this, Calendario.class);
        startActivityForResult(i, 1);
    }

    public void MostrarHora(View v) {
        Intent i = new Intent(this, Hora.class);
        startActivityForResult(i, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            String dia = data.getStringExtra("dia");
            String mes = data.getStringExtra("mes");
            String anio = data.getStringExtra("año");
            TextView fecha = (TextView) findViewById(R.id.fecha);
            int mesReal = Integer.parseInt(mes) + 1;
            fecha.setText(dia + "/" + String.valueOf(mesReal) + "/" + anio);
        }
        if (requestCode == 2) {
            int hora = Integer.parseInt(data.getStringExtra("hora"));
            int minuto = Integer.parseInt(data.getStringExtra("minuto"));
            TextView tiempo = (TextView) findViewById(R.id.hora);
            tiempo.setText(String.format(Locale.ENGLISH, "%02d:%02d", hora, minuto));
        }
    }
}
