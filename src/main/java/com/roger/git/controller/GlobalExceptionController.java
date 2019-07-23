package com.roger.git.controller;

import com.roger.git.bean.ApiCustomResponse;
import com.roger.git.exception.GitException;
import com.roger.git.exception.RepoUserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

/**
 * Global exception handler
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionController {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({GitException.class, HttpClientErrorException.class,Exception.class})
    public final ResponseEntity<ApiCustomResponse> handleAllExceptions(Exception ex)
    {
        return new ResponseEntity<>(new ApiCustomResponse(LocalDateTime.now(),HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ApiCustomResponse> handleBadRequestExceptions(MethodArgumentNotValidException mnv)
    {
        return new ResponseEntity<>(new ApiCustomResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(),mnv.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({RepoUserNotFoundException.class})
    public final ResponseEntity<ApiCustomResponse> handleNotFoundExceptions(RepoUserNotFoundException ex)
    {
        return new ResponseEntity<>(new ApiCustomResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(),ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ApiCustomResponse>  constraintExceptionHandler(ConstraintViolationException e) {
        return new ResponseEntity<>(new ApiCustomResponse(LocalDateTime.now(),HttpStatus.FORBIDDEN.value(),e.getMessage()), HttpStatus.FORBIDDEN);

    }



}
