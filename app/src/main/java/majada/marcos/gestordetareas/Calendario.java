package majada.marcos.gestordetareas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;

/**
 * Esta clase muestra un calendario para escoger la fecha en la que la tarea tendria que haberse terminado.
 */

public class Calendario extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendario);
    }

    public void Guardar(View v) {
        DatePicker fecha = (DatePicker) findViewById(R.id.datePicker);
        String anio = String.valueOf(fecha.getYear());
        String mes = String.valueOf(fecha.getMonth());
        String dia = String.valueOf(fecha.getDayOfMonth());
        Intent retorno = new Intent();
        retorno.putExtra("dia", dia);
        retorno.putExtra("mes", mes);
        retorno.putExtra("año", anio);
        setResult(Activity.RESULT_OK, retorno);
        finish();
    }
}
