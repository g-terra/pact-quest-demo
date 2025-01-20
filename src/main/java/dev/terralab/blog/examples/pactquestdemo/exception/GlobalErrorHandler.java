package dev.terralab.blog.examples.pactquestdemo.exception;

import dev.terralab.blog.examples.pactquestdemo.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler(QuestNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleQuestNotFoundException(QuestNotFoundException ex) {
        return new ErrorResponseDto("NOT_FOUND", ex.getMessage());
    }
    
    @ExceptionHandler(InvalidStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleInvalidStatusException(InvalidStatusException ex) {
        return new ErrorResponseDto("BAD_REQUEST", ex.getMessage());
    }
    
    @ExceptionHandler(InvalidStatusTransitionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleInvalidStatusTransitionException(InvalidStatusTransitionException ex) {
        return new ErrorResponseDto("INVALID_ACTION", ex.getMessage());
    }
}
