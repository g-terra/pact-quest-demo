package dev.terralab.blog.examples.pactquestdemo.client.base;

public record ApiResponse<T>(T data, ApiError error, boolean success) {
    public ApiResponse(T data) {
        this(data, null, true);
    }

    public ApiResponse(ApiError error) {
        this(null, error, false);
    }
}
