package me.roinujnosde.vaichover.io;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.roinujnosde.vaichover.model.Previsao;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Recebedor {
    // TODO: 29/10/18 implementar descrição do clima
    // TODO: 29/10/18 adicionar icones do clima

    private static final String API_KEY = "76db612a087439b38b352f310da94a91";

    /**
     * Pega a previsão utilizando a API do openweathermap
     *
     * @param cidade cidade
     * @return a previsão ou null se houve erro
     */
    public Previsao getPrevisao(String cidade) {
        try {
            System.out.println("Conectando...");
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + cidade + "&APIKEY=" + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.addRequestProperty("accept", "application/json");

            int responseCode = connection.getResponseCode();
            //sucesso
            if (responseCode == 200) {
                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                Gson gson = new Gson();
                JsonObject response = gson.fromJson(isr, JsonObject.class);
                JsonObject main = response.get("main").getAsJsonObject();

                double temperatura = main.get("temp").getAsDouble();
                cidade = response.get("name").getAsString();
                String pais = response.get("sys").getAsJsonObject().get("country").getAsString();
                double maxima = main.get("temp_max").getAsDouble();
                double minima = main.get("temp_min").getAsDouble();
                double vento = response.get("wind").getAsJsonObject().get("speed").getAsDouble();
                int umidade = main.get("humidity").getAsInt();

                return new Previsao(cidade, pais, temperatura, maxima, minima, vento, umidade);
            }
        } catch (ProtocolException | MalformedURLException ignored) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
