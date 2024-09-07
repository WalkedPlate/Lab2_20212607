package com.example.lab2_20212607;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab2_20212607.bean.Resultado;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    private String[] palabras;
    private Random random;
    private String palabraActual;
    private TextView[] charViews;
    private LinearLayout palabraLayout;
    private LetterAdapter adapter;
    private GridView gridView;
    private int numCorr;
    private int numChars;
    private ImageView[] partes;
    private int sizeParts=6;
    private int parteActual;
    private boolean terminoJuego;


    private List<Resultado> resultados;
    private long startTime;
    private long endTime;
    private long elapsed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        String nombreJugador = intent.getStringExtra("nombreJugador");


        palabras = getResources().getStringArray(R.array.palabras);
        palabraLayout = findViewById(R.id.palabraLayout);
        gridView = findViewById(R.id.letras);
        random = new Random();
        resultados = new ArrayList<>();

        partes = new ImageView[sizeParts];
        partes[0] = findViewById(R.id.head);
        partes[1] = findViewById(R.id.torso);
        partes[2] = findViewById(R.id.brazoizq);
        partes[3] = findViewById(R.id.brazoder);
        partes[4] = findViewById(R.id.piernaizq);
        partes[5] = findViewById(R.id.piernader);


        jugar();

        Button nuevoJuego = findViewById(R.id.nuevoJuego);
        nuevoJuego.setOnClickListener(view -> {

            TextView resultadoTextView = findViewById(R.id.Resultado);
            resultadoTextView.setText("");

            if (!terminoJuego) { //No terminó el juego
                Resultado resultado = new Resultado();
                resultado.setCancelo(true);
                resultado.setTiempo(1);
                resultados.add(resultado);
            }

            jugar(); // Solo se llama una vez
        });

        //Ir a estadísticas

        ImageButton estadisticas = findViewById(R.id.estadisticas);

        estadisticas.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity2.this, MainActivity3.class);
            intent1.putExtra("listaResultados", (Serializable) resultados);
            intent1.putExtra("nombreJugador", nombreJugador);
            startActivity(intent1);
        });

        // Retroceder botón
        ImageButton retroceder = findViewById(R.id.retroceder);
        retroceder.setOnClickListener(v -> {
            finish();
        });


        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //aquí llegamos luego del setResult(RESULT_OK,intent);
                    if (result.getResultCode() == RESULT_OK) {
                        jugar();
                    }
                }
        );


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void jugar(){

        terminoJuego = false;

        //Controlar el tiempo
        startTime = System.currentTimeMillis();
        elapsed = 0;


        String nuevaPalabra=palabras[random.nextInt(palabras.length)];

        while (nuevaPalabra.equals(palabraActual)){
            nuevaPalabra=palabras[random.nextInt(palabras.length)];
        }
        palabraActual=nuevaPalabra;

        charViews = new TextView[palabraActual.length()];

        palabraLayout.removeAllViews();

        for (int i=0; i<palabraActual.length();i++){
            charViews[i] = new TextView(this);
            charViews[i].setText(""+palabraActual.charAt(i));
            charViews[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            charViews[i].setGravity(Gravity.CENTER);
            charViews[i].setTextColor(Color.WHITE);
            charViews[i].setBackgroundResource(R.drawable.letter_bg);
            palabraLayout.addView(charViews[i]);

        }
        adapter = new LetterAdapter(this);
        gridView.setAdapter(adapter);
        numCorr=0;
        parteActual=0;
        numChars=palabraActual.length();

        for(int i = 0; i < sizeParts; i++){
            partes[i].setVisibility(View.INVISIBLE);
        }

    }

    public void letraPresionada(View view){
        String letra = ((TextView) view).getText().toString();
        char letraChar=letra.charAt(0);

        view.setEnabled(false); //desactivar el botón

        boolean correct = false;

        //Recorremos la palabra
        for(int i=0;i<palabraActual.length();i++){
            if(palabraActual.charAt(i)==letraChar){
                correct = true;
                numCorr++;
                charViews[i].setTextColor(Color.BLACK); //Pintamos la letra si es acertada
            }
        }

        if(correct){
            if(numCorr == numChars){ //Ganar el juego

                terminoJuego = true;
                endTime = System.currentTimeMillis();
                long timeElapsed = endTime - startTime; // Tiempo en milisegundos
                elapsed = timeElapsed / 1000;

                Resultado resultado = new Resultado();
                resultado.setCancelo(false);
                resultado.setTiempo(elapsed);
                resultados.add(resultado);
                //Log.d("resultado",String.valueOf(resultado.getTiempo()));

                disableAllButtons();

                TextView resultadoTextView = findViewById(R.id.Resultado);
                resultadoTextView.setText("Ganó / Terminó en " + elapsed +" s");

            }
        } else if (parteActual < sizeParts) { //Tecla errónea
            partes[parteActual].setVisibility(View.VISIBLE);
            parteActual++;
        }
        else { //Perder el juego

            terminoJuego = true;
            endTime = System.currentTimeMillis();
            long timeElapsed = endTime - startTime; // Tiempo en milisegundos
            elapsed = timeElapsed / 1000;

            Resultado resultado = new Resultado();
            resultado.setCancelo(false);
            resultado.setTiempo(elapsed);
            resultados.add(resultado);
            //Log.d("resultado",String.valueOf(resultado.getTiempo()));
            //Log.d("lista",String.valueOf(resultados.get(0).getTiempo()));

            disableAllButtons();

            TextView resultadoTextView = findViewById(R.id.Resultado);
            resultadoTextView.setText("Perdió / Terminó en " + elapsed +" s");
        }


    }

    public void disableAllButtons(){
        for(int i = 0; i<gridView.getChildCount();i++){
            gridView.getChildAt(i).setEnabled(false);
        }
    }

}