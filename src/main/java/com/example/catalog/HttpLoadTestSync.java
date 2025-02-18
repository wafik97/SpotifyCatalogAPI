package com.example.catalog;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class HttpLoadTestSync {
    private static final int REQUEST_COUNT = 10;
    private static final String TARGET_URL = "http://localhost:8080/";

    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        List<Long> responseTimes = new ArrayList<>();

        for (int i = 0; i < REQUEST_COUNT; i++) {
            System.out.println("Sending request: " + i);
            responseTimes.add(sendRequest(client));
        }

        double avgTime = responseTimes.stream().mapToLong(Long::longValue).average().orElse(0);
        System.out.println("Average Response Time: " + avgTime + " ms");
    }

    private static long sendRequest(HttpClient client) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TARGET_URL))
                .GET()
                .build();

        Instant start = Instant.now();

        try {
            client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (Exception e) {
            System.err.println("Request failed: " + e.getMessage());
        }

        return Duration.between(start, Instant.now()).toMillis();
    }
}