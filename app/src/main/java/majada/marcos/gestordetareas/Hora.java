package majada.marcos.gestordetareas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.NumberPicker;

/**
 * Esta clase muestra un NumberPicker para escoger la hora a la que se debe entregar la tarea.
 */

public class Hora extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hora);
        NumberPicker escogerHora = (NumberPicker) findViewById(R.id.hora);
        NumberPicker escogerMinuto = (NumberPicker) findViewById(R.id.minuto);
        NumberPicker escogerModo = (NumberPicker) findViewById(R.id.AMPM);
        //Aqui se indican los valores que apareceran en cada uno de los NumberPicker
        String[] min = {"0", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
        String[] AMPM = {"AM", "PM"};
        escogerHora.setMinValue(0);
        escogerHora.setMaxValue(11);
        escogerMinuto.setMinValue(0);
        escogerMinuto.setMaxValue(min.length - 1);
        escogerMinuto.setDisplayedValues(min);
        escogerModo.setMinValue(0);
        escogerModo.setMaxValue(AMPM.length - 1);
        escogerModo.setDisplayedValues(AMPM);
    }

    public void Guardar(View v) {
        NumberPicker escogerHora = (NumberPicker) findViewById(R.id.hora);
        NumberPicker escogerMinuto = (NumberPicker) findViewById(R.id.minuto);
        NumberPicker escogerModo = (NumberPicker) findViewById(R.id.AMPM);
        String hora;
        String minuto;
        if (escogerModo.getValue() == 1) {
            //En caso de que sea una hora PM se la suman 12 al valor obtenido del NumberPicker hora
            int nuevaHora = escogerHora.getValue() + 12;
            hora = String.valueOf(nuevaHora);
            //El NumberPicker minuto devuelve los valores 0-11, por lo que se multiplican por 5.
            minuto = String.valueOf(escogerMinuto.getValue() * 5);
        } else {
            hora = String.valueOf(escogerHora.getValue());
            minuto = String.valueOf(escogerMinuto.getValue() * 5);
        }
        Intent retorno = new Intent();
        retorno.putExtra("hora", hora);
        retorno.putExtra("minuto", minuto);
        setResult(Activity.RESULT_OK, retorno);
        finish();
    }
}
