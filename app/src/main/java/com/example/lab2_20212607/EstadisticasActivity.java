package com.example.lab2_20212607;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab2_20212607.bean.Resultado;

import java.io.Serializable;
import java.util.List;

public class EstadisticasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        // botón de retroceso
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Habilitar el botón
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Acción para el botón
        toolbar.setNavigationOnClickListener(v -> finish());


        Intent intent = getIntent();
        String nombreJugador = intent.getStringExtra("nombreJugador");
        List<Resultado> resultados = (List<Resultado>) intent.getSerializableExtra("listaResultados");


        TextView jugador = findViewById(R.id.jugador);
        jugador.setText("Jugador: " + nombreJugador);

        // Obtén el LinearLayout del archivo XML
        LinearLayout resultadosLayout = findViewById(R.id.listaresultados);

        int i = 1;
        for (Resultado resultado : resultados) {
            TextView textView = new TextView(this);

            if (resultado.isCancelo()) {
                textView.setText("Juego " + i + ": Canceló");
            } else {
                textView.setText("Juego " + i + ": Terminó en " + resultado.getTiempo() + " s");
            }

            textView.setTextSize(18);
            textView.setPadding(16, 16, 16, 16);
            resultadosLayout.addView(textView);
            i++;
        }


        //Botón de nuevo juego
        Button nuevoJuego = findViewById(R.id.nuevoJuego);
        nuevoJuego.setOnClickListener(v -> {
            Intent intent1 = new Intent();
            setResult(RESULT_OK, intent1);
            finish();
        });


        // Retroceder botón
        /*
        ImageButton retroceder = findViewById(R.id.retroceder);
        retroceder.setOnClickListener(v -> {
            finish();
        });

         */



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main3), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // No inflamos el App Bar en esta vista (no mostramos el botón)

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_estadisticas, menu);
        return true;
    }

     */


}