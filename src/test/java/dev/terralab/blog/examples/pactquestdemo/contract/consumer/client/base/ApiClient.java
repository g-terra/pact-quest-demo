package dev.terralab.blog.examples.pactquestdemo.contract.consumer.client.base;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ApiClient {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String baseUrl;

    protected <T> ApiResponse<T> get(String endpoint, Class<T> responseType) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + endpoint))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                T data = objectMapper.readValue(response.body(), responseType);
                return new ApiResponse<>(data);
            } else {
                ApiError error = parseError(response.body());
                return new ApiResponse<>(error);
            }

        } catch (Exception e) {
            ApiError error = new ApiError("CLIENT_ERROR", "An problem occurred while processing the request." + e.getMessage());
            return new ApiResponse<>(error);
        }
    }

    protected <T, R> ApiResponse<R> put(String endpoint, T requestBody, Class<R> responseType) {
        try {
            String requestBodyJson = objectMapper.writeValueAsString(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + endpoint))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                R data = objectMapper.readValue(response.body(), responseType);
                return new ApiResponse<>(data);
            } else {
                ApiError error = parseError(response.body());
                return new ApiResponse<>(error);
            }

        } catch (Exception e) {
            ApiError error = new ApiError("CLIENT_ERROR", "An problem occurred while processing the request." + e.getMessage());
            return new ApiResponse<>(error);
        }
    }

    protected <T, R> ApiResponse<R> post(String endpoint, T requestBody, Class<R> responseType) {
        try {
            String requestBodyJson = objectMapper.writeValueAsString(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + endpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                R data = objectMapper.readValue(response.body(), responseType);
                return new ApiResponse<>(data);
            } else {
                ApiError error = parseError(response.body());
                return new ApiResponse<>(error);
            }

        } catch (Exception e) {
            ApiError error = new ApiError("CLIENT_ERROR", "An problem occurred while processing the request." + e.getMessage());
            return new ApiResponse<>(error);
        }
    }

    private ApiError parseError(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, ApiError.class);
        } catch (Exception e) {
            return new ApiError("UNEXPECTED_RESPONSE", "Failed to parse error response: " + responseBody);
        }
    }

}
