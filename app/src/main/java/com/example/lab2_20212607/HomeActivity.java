package com.example.lab2_20212607;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab2_20212607.bean.Jugador;

public class HomeActivity extends AppCompatActivity {

    private TextView tituloTelegame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        EditText nombreEntrada = findViewById(R.id.nombreEntrada);
        Button jugarButton = findViewById(R.id.playButton);
        jugarButton.setEnabled(false);

        tituloTelegame = findViewById(R.id.telegame);

        registerForContextMenu(tituloTelegame);


        // Lógica del botón de jugar
        nombreEntrada.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Activamos el botón si hay texto, de lo contrario lo desactivamos
                jugarButton.setEnabled(!charSequence.toString().trim().isEmpty());
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Al presionar el botón de jugar
        jugarButton.setOnClickListener(view -> {
            String nombreJugador= nombreEntrada.getText().toString();

            // Aseguramos que hay texto en el campo
            if (!nombreJugador.isEmpty()) {
                // Ir al juego
                Intent intent = new Intent(HomeActivity.this, TeleGameActivity.class);
                intent.putExtra("nombreJugador", nombreJugador);
                startActivity(intent);
            } else {
                // Adicionalmente mostramos un mensaje si no hay texto ingresado (medida extra)
                Toast.makeText(HomeActivity.this, "Por favor ingrese su nombre", Toast.LENGTH_SHORT).show();
            }


        });





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void goToGame(View view) {

        EditText editText = findViewById(R.id.nombreEntrada);
        String nombreJugador= editText.getText().toString();
        Jugador jugador = new Jugador(nombreJugador);
        Intent intent = new Intent(this, TeleGameActivity.class);
        //se envia un objeto
        intent.putExtra("jugador", jugador);
        startActivity(intent);

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.color_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        
        if(item.getItemId() == R.id.color_green){
            tituloTelegame.setTextColor(Color.GREEN);
            return true;
        } else if (item.getItemId() == R.id.color_red) {
            tituloTelegame.setTextColor(Color.RED);
            return true;
        } else if (item.getItemId() == R.id.color_purple) {
            tituloTelegame.setTextColor(Color.MAGENTA);
            return true;
        }
        else {
            return super.onContextItemSelected(item);
        }
    }


}