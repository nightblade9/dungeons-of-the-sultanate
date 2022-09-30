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

    /**
     * Makes a GET call. Doesn't accept a request, because you can't pass a body in HTML to a GET call.
     * Instead, append it to the URL, e.g. ?userId=foo
     */
    public <T> T get(String url, Class<T> responseType) {
        return this.call(url, HttpMethod.GET, null, responseType);
    }

    public <T> T post(String url, Object request, Class<T> responseType) {
        return this.call(url, HttpMethod.POST, request, responseType);
    }

    // TODO: unit test (white-box)
    private <T> T call(String url, HttpMethod method, Object request, Class<T> responseType)
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
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(headerName, value);
        return headers;
    }
}
