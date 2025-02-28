package com.myblog.myblog.exception;

import com.myblog.myblog.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
//how do you handle exception(interview)
//we crerate exception clasas ,we extend exception class to responseentity exception handler
//we mark that with @controller addvice and we devlope the method called as@exceptionhandler.
@ControllerAdvice//whatever exception occurs in the project comes here
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException
            (ResourceNotFoundException exception,
    WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> GlobalResourceNotFoundException(Exception exception,
             WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

    }



}
