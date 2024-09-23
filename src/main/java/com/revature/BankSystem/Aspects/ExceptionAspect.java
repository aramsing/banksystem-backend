package com.revature.BankSystem.Aspects;

import com.revature.BankSystem.Exceptions.DataNotFoundException;
import com.revature.BankSystem.Exceptions.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAspect {
    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidInputException(InvalidInputException iie) {
        return iie.getMessage();
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String dataNotFoundException(DataNotFoundException dnfe) {
        return dnfe.getMessage();
    }
}