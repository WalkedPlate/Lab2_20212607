package com.example.lab2_20212607;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab2_20212607.bean.Resultado;

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


    private List<Resultado> resultados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        palabras = getResources().getStringArray(R.array.palabras);
        palabraLayout = findViewById(R.id.palabraLayout);
        gridView = findViewById(R.id.letras);
        random = new Random();

        partes = new ImageView[sizeParts];
        partes[0] = findViewById(R.id.head);
        partes[1] = findViewById(R.id.torso);
        partes[2] = findViewById(R.id.brazoizq);
        partes[3] = findViewById(R.id.brazoder);
        partes[4] = findViewById(R.id.piernaizq);
        partes[5] = findViewById(R.id.piernader);


        jugar();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void jugar(){

        String nuevaPalabra=palabras[random.nextInt(palabras.length)];

        while (nuevaPalabra.equals(palabraActual)){
            nuevaPalabra=palabras[random.nextInt(palabras.length)];
        }
        palabraActual=nuevaPalabra;

        charViews = new TextView[palabraActual.length()];

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

        resultados = new ArrayList<>();



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
                disableAllButtons();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Win");
                builder.setMessage("Felicidades");
                builder.setPositiveButton("Jugar de Nuevo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity2.this.jugar();
                    }
                });

                builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity2.this.finish();
                    }
                });
                builder.show();
            }
        } else if (parteActual < sizeParts) { //Tecla errónea
            partes[parteActual].setVisibility(View.VISIBLE);
            parteActual++;
        }
        else { //Perder el juego
            disableAllButtons();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Perdiste");
            builder.setMessage("Lose");
            builder.setPositiveButton("Jugar de Nuevo", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity2.this.jugar();
                }
            });

            builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity2.this.finish();
                }
            });
            builder.show();
        }


    }

    public void disableAllButtons(){
        for(int i = 0; i<gridView.getChildCount();i++){
            gridView.getChildAt(i).setEnabled(false);
        }
    }

}