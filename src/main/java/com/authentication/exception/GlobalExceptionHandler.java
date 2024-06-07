package com.authentication.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.authentication.dto.Response;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception ex) {
        return Response.getResponse("An unexpected error occurred: " + ex.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Response> handleException(NumberFormatException ex) {
        return Response.getResponse("An unexpected error occurred: " + ex.getMessage(), null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Response> handleException(MethodArgumentTypeMismatchException ex) {
        return Response.getResponse("An error occurred! Please provide a number, not an alphabet, in the URL.", null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Response> handleException(NoResourceFoundException ex) {
        return Response.getResponse("Please provide a correct URL!", null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Response> handleException(CustomException ex) {
        return Response.getResponse(ex.getMessage(), null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<Response> handleException(MultipartException ex) {
        return Response.getResponse("You must provide a 'file' to send the request!", null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Response> handleException(MissingServletRequestParameterException ex) {
        return Response.getResponse("Required parameter is missing: " + ex.getParameterName(), null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Response> handleException(HttpRequestMethodNotSupportedException ex) {
        return Response.getResponse("Request method '" + ex.getMethod() + "' is not supported", null, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Response> handleException(HttpMediaTypeNotSupportedException ex) {
        return Response.getResponse("Media type is not supported!", null, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response> handleException(DataIntegrityViolationException ex) {
        return Response.getResponse("Duplicate record found, please verify!", null, HttpStatus.CONFLICT);
    }
}
