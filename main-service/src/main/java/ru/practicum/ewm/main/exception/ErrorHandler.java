package ru.practicum.ewm.main.exception;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Arrays;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        log.warn("404 {}", e.getMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        return new ApiError(HttpStatus.NOT_FOUND,
                "Not found exception",
                e.getMessage(),
                LocalDateTime.now(),
                Arrays.stream(e.getStackTrace()).toList());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(final BadRequestException e) {
        log.warn("400 {}", e.getMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return new ApiError(HttpStatus.BAD_REQUEST,
                "Bad request",
                e.getMessage(),
                LocalDateTime.now(),
                Arrays.stream(e.getStackTrace()).toList());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.warn("400 {}", e.getMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return new ApiError(HttpStatus.BAD_REQUEST,
                "Argument not valid",
                e.getMessage(),
                LocalDateTime.now(),
                Arrays.stream(e.getStackTrace()).toList());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(final ValidationException e) {
        log.warn("400 {}", e.getMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return new ApiError(HttpStatus.BAD_REQUEST,
                "Parameter not valid",
                e.getMessage(),
                LocalDateTime.now(),
                Arrays.stream(e.getStackTrace()).toList());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(final MissingServletRequestParameterException e) {
        log.warn("400 {}", e.getMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return new ApiError(HttpStatus.BAD_REQUEST,
                "Required request parameter not found",
                e.getMessage(),
                LocalDateTime.now(),
                Arrays.stream(e.getStackTrace()).toList());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(final ConflictException e) {
        log.warn("409 {}", e.getMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return new ApiError(HttpStatus.CONFLICT,
                "Required request parameter not found",
                e.getMessage(),
                LocalDateTime.now(),
                Arrays.stream(e.getStackTrace()).toList());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(final DataIntegrityViolationException e) {
        log.warn("409 {}", e.getMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return new ApiError(HttpStatus.CONFLICT,
                "DataIntegrityViolationException",
                e.getMessage(),
                LocalDateTime.now(),
                Arrays.stream(e.getStackTrace()).toList());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleInternalServerException(final Exception e) {
        log.warn("500 {} {}", e.getClass(), e.getMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                e.getMessage(),
                LocalDateTime.now(),
                Arrays.stream(e.getStackTrace()).toList());
    }


}
