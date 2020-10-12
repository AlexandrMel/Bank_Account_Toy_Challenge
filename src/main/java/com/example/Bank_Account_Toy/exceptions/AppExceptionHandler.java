package com.example.Bank_Account_Toy.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.Bank_Account_Toy.ui.model.response.ErrorMessage;


// Exception wrappers with Spring
@ControllerAdvice
public class AppExceptionHandler {

    //Create Exception Wrapper that makes it more specific - handleAccountServiceException - with only 2 fields Time stamp and Message
    @ExceptionHandler(value = {AccountServiceException.class})
    public ResponseEntity<Object> handleAccountServiceException(AccountServiceException ex, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Create a generic wrapper for all other exceptions - with only 2 fields Time stamp and Message
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
