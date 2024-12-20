package com.example.trabajoiot;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // ThingSpeak API URL y clave de escritura
    private static final String THINGSPEAK_URL = "https://api.thingspeak.com/update";
    private static final String API_KEY = "TLOQ7KB4R63I9CUS"; // Reemplaza con tu clave API de escritura

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button apagarAlarmaButton = findViewById(R.id.btnApagarAlarma);
        apagarAlarmaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarComandoApagarAlarma();
            }
        });
    }

    private void enviarComandoApagarAlarma() {
        // Crear un hilo para manejar la petición HTTP
        new Thread(() -> {
            try {
                // Construir la URL con el comando para apagar la alarma (solo field2)
                String urlString = THINGSPEAK_URL + "?api_key=" + API_KEY + "&field2=0";
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET"); // Usar método GET
                connection.connect();

                // Verificar respuesta del servidor
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    runOnUiThread(() -> Toast.makeText(this, "Comando enviado con éxito", Toast.LENGTH_SHORT).show());
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Error al enviar comando", Toast.LENGTH_SHORT).show());
                }

                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
            //aaaaaaaaaaaaaaaaaa
        }).start();
    }

}
}
