package com.wipro.calculadoraFrete.services;

import com.wipro.calculadoraFrete.Exception.CepNotFoundException;
import com.wipro.calculadoraFrete.entities.ViaCepResponse;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

@Service
public class BuscarCep extends ViaCEPBase {

    @Override
    public final ViaCepResponse buscar(String cep) throws CepNotFoundException {

        String cepLimpo = cep.replaceAll("\\D", "").replace("-", ""); //Limpando dado do usuario
        String encodedCep = URLEncoder.encode(cepLimpo, StandardCharsets.UTF_8);
        String url = "http://viacep.com.br/ws/" + encodedCep + "/json/"; //criando url do request;

        String response = getHttpGET(url);

        if (response != null && !response.isEmpty()) {
            JSONObject obj = new JSONObject(response);
            if (obj.has("erro")) {
                throw new CepNotFoundException("Erro interno da API");
            } else {
                ViaCepResponse novoCEP = new ViaCepResponse(
                        obj.getString("cep"),
                        obj.getString("logradouro"),
                        obj.getString("complemento"),
                        obj.getString("bairro"),
                        obj.getString("localidade"),
                        obj.getString("uf"));

                if (Events instanceof ViaCEPEvents) {
                    Events.onCEPSuccess(this);
                }

                return novoCEP;
            }
        } else {
            if (Events instanceof ViaCEPEvents) {
                Events.onCEPError(currentCEP);
            }

            throw new CepNotFoundException("Cep não encontrado");
        }
    }

    public final String getHttpGET(String urlToRead) throws CepNotFoundException {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

        } catch (MalformedURLException | ProtocolException ex) {
            // verifica os Eventos
            if (Events instanceof ViaCEPEvents) {
                Events.onCEPError(currentCEP);
            }

            throw new CepNotFoundException(ex.getMessage());
        } catch (IOException ex) {
            // verifica os Eventos
            if (Events instanceof ViaCEPEvents) {
                Events.onCEPError(currentCEP);
            }

            throw new CepNotFoundException(ex.getMessage());
        }

        return result.toString();
    }

}
