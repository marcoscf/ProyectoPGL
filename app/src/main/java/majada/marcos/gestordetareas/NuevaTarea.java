package majada.marcos.gestordetareas;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;

import static majada.marcos.gestordetareas.R.id.fecha;

/**
 * Esta clase permite modificar los datos de una tarea a partir de la fila seleccionada.
 */

public class NuevaTarea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_tarea);
        TextView t4 = (TextView) findViewById(fecha);
        TextView t5 = (TextView) findViewById(R.id.hora);
        String dia = (String) DateFormat.format("dd", new Date());
        String mes = (String) DateFormat.format("MM", new Date());
        String anio = (String) DateFormat.format("yyyy", new Date());
        String hora = (String) DateFormat.format("kk", new Date());
        String minuto = (String) DateFormat.format("mm", new Date());
        int diaMes = (Integer.parseInt(dia) + 7);
        t4.setText(String.valueOf(diaMes) + "/" + mes + "/" + anio);
        t5.setText(hora + ":" + minuto);
    }

    public void Almacenar(View v) {
        //Creamos variables para los campos de la actividad nueva_tarea.
        EditText t1 = (EditText) findViewById(R.id.et1);
        RadioGroup t2 = (RadioGroup) findViewById(R.id.rg1);
        RadioGroup t3 = (RadioGroup) findViewById(R.id.rg2);
        TextView t4 = (TextView) findViewById(fecha);
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
        //Guardamos los datos en la BD.
        AdministradorSQLite admin = new AdministradorSQLite(this, "TareasBD", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("estado", textoEstado);
        registro.put("prioridad", textoPrioridad);
        registro.put("fecha", fecha);
        registro.put("hora", hora);
        bd.insert("tareas", null, registro);
        bd.close();
        finish();
    }

    public void Atras(View v) {
        finish();
    }

    public void Reset(View v) {
        //Asignamos valores por defecto a los campos.
        EditText t1 = (EditText) findViewById(R.id.et1);
        RadioGroup t2 = (RadioGroup) findViewById(R.id.rg1);
        RadioGroup t3 = (RadioGroup) findViewById(R.id.rg2);
        TextView t4 = (TextView) findViewById(R.id.fecha);
        TextView t5 = (TextView) findViewById(R.id.hora);
        t1.setText("");
        t2.check(R.id.rb2);
        t3.check(R.id.rb4);
        //Obtenemos fecha y hora actual a partir de un objeto Date.
        String dia = (String) DateFormat.format("dd", new Date());
        String mes = (String) DateFormat.format("MM", new Date());
        String anio = (String) DateFormat.format("yyyy", new Date());
        String hora = (String) DateFormat.format("kk", new Date());
        String minuto = (String) DateFormat.format("mm", new Date());
        int diaMes = (Integer.parseInt(dia) + 7);
        t4.setText(String.valueOf(diaMes) + "/" + mes + "/" + anio);
        t5.setText(hora + ":" + minuto);
    }

    //Abrimos las actividades para escoger fecha y hora.
    public void MostrarCalendario(View v) {
        Intent i = new Intent(this, Calendario.class);
        startActivityForResult(i, 1);
    }

    public void MostrarHora(View v) {
        Intent i = new Intent(this, Hora.class);
        startActivityForResult(i, 2);
    }

    //Insertamos la fecha y hora selecionada en sus respectivos campos.
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
            //Con el %02d mostrara el 0 en caso de que sea un valor menor de 10.
            tiempo.setText(String.format(Locale.ENGLISH, "%02d:%02d", hora, minuto));
        }
    }
}