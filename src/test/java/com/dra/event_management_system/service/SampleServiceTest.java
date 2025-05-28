package com.dra.event_management_system.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// @RestClientTest(SampleService.class)
@ExtendWith(MockitoExtension.class)
public class SampleServiceTest {

    @Mock
    private HttpResponse<String> httpResponse;

    @Mock
    private HttpClient httpClient;

    @InjectMocks
    private SampleService sampleService;

    @SuppressWarnings("unchecked")
    @Test
    public void testFetchExternaData() throws IOException, InterruptedException{

        String sampleResponse = "{\"id\":223344,\"name\":\"Test User\"}";

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(httpResponse);

        when(httpResponse.body()).thenReturn(sampleResponse);

        String result = sampleService.fetchExternaData();

        assertNotNull(result);
        assertTrue(result.contains("Test User"));
    }

}
