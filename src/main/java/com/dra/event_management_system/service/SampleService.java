package com.dra.event_management_system.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final HttpClient httpClient;

    public String fetchExternaData() throws IOException, InterruptedException{
        URI uri = URI.create("https://jsonplaceholder.typicode.com/posts");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                                        .uri(uri)
                                        .GET()
                                        .build();

        HttpResponse<String> response = httpClient.send(
                                                httpRequest, 
                                                HttpResponse.BodyHandlers.ofString()
                                            );
                                            
        return response.body();
    }

}
