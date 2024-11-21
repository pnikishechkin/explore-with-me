package ru.practicum.ewm.stats.exception;

public class ApiError {
    String error;

    public ApiError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
