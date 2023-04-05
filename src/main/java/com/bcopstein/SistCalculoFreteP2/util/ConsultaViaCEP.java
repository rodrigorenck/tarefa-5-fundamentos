package com.bcopstein.SistCalculoFreteP2.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static java.time.temporal.ChronoUnit.MINUTES;

// Para maiores informações sobre a biblioteca Gson consulte:
// https://github.com/google/gson/blob/master/UserGuide.md

// Um conversor de JSON para o DTO Java: 
// https://app.quicktype.io/ 

// Material extra sobre Gson:
// https://www.studytrails.com/2016/09/12/java-google-json-parse-json-to-java/
public class ConsultaViaCEP {
    public static final String urlbase = "https://viacep.com.br/ws";

	private HttpRequest httpRequest;
	private HttpResponse<String> httpResponse;

	public ConsultaViaCEP(){
		httpRequest = null;
		httpResponse = null;
	}

    public HttpRequest getHttpRequest() {
		return httpRequest;
	}

	public HttpResponse<String> getHttpResponse() {
		return httpResponse;
	}

	public static String montaConsultaHTTP(String urlStr) throws UnsupportedEncodingException{
        StringBuffer consulta = new StringBuffer();
        consulta.append(urlbase);
        consulta.append(urlStr);
        consulta.append("/json");
		return consulta.toString();
	}

	public ResultadoPesquisaCEP submeteConsultaCEP(String urlStr) throws UnsupportedEncodingException {
		var url = ConsultaViaCEP.montaConsultaHTTP(urlStr);
        try {
            HttpClient httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.of(1, MINUTES))
                    .build();

            httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .build();

            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            String json = httpResponse.body();
            ResultadoPesquisaCEP objResp = gson.fromJson(json,ResultadoPesquisaCEP.class);
            return objResp;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

	public List<ResultadoPesquisaCEP> submeteConsultaLogradouro(String urlStr) throws UnsupportedEncodingException {
		var url = ConsultaViaCEP.montaConsultaHTTP(urlStr);
        try {
            HttpClient httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.of(1, MINUTES))
                    .build();

            httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .build();

            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            String json = httpResponse.body();
            TypeToken<List<ResultadoPesquisaCEP>> collectionType = new TypeToken<>(){};
            List<ResultadoPesquisaCEP> lstResp = gson.fromJson(json,collectionType);
            return lstResp;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
