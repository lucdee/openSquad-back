package com.v1.opensquad.service.exception;


import com.v1.opensquad.service.exception.model.Error;
import com.v1.opensquad.service.exception.model.ErrorErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProfileDataException.class)
    public ResponseEntity<Error> consultaDadosException(final ProfileDataException consultaDadosException){
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final Error error = new Error();
        error.setTimestamp(String.valueOf(System.currentTimeMillis()));
        error.setStatus(httpStatus.value());
        final ErrorErrors errorErrors = new ErrorErrors();
        errorErrors.setCode(String.valueOf(httpStatus.value()));
        errorErrors.setDescription(consultaDadosException.getDescription());
        error.setErrors(Arrays.asList(errorErrors));
        return ResponseEntity.status(httpStatus).body(error);
    }

    @ExceptionHandler(AuthDataException.class)
    public ResponseEntity<Error> consultaDadosException(final AuthDataException consultaDadosException){
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final Error error = new Error();
        error.setTimestamp(String.valueOf(System.currentTimeMillis()));
        error.setStatus(httpStatus.value());
        final ErrorErrors errorErrors = new ErrorErrors();
        errorErrors.setCode(String.valueOf(httpStatus.value()));
        errorErrors.setDescription(consultaDadosException.getDescription());
        error.setErrors(Arrays.asList(errorErrors));
        return ResponseEntity.status(httpStatus).body(error);
    }





}
