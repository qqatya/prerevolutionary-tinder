package ru.liga.prerevolutionarytinderserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.liga.prerevolutionarytinderserver.model.ErrorInfo;

@Slf4j
@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo processException(RuntimeException e) {
        log.error("Unexpected error", e);
        return new ErrorInfo(e.getMessage());
    }
}
