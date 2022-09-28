package com.deengames.dungeonsofthesultanate.services.web.security.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class ServiceToServiceClient {

    @Autowired
    private Environment environment;

    // TODO: unit test (white-box)
    public <T> T post(String url, HttpMethod method, Object request, Class<T> responseType)
    {
        var secret = environment.getProperty("dots.service_to_service_secret");
        var headers = createHeadersWith("Client-Secret", secret);
        var entity = new HttpEntity<>(request, headers);

        var restClient = new RestTemplate();
        var result = restClient.exchange(url, method, entity, responseType);
        return result != null ? result.getBody() : null;
    }

    private static HttpHeaders createHeadersWith(String headerName, String value)
    {
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add(headerName, value);
        return headers;
    }
}
