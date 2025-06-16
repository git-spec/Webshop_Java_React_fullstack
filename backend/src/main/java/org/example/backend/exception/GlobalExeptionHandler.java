package org.example.backend.exception;

import org.apache.coyote.BadRequestException;
import org.example.backend.model.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExeptionHandler {

    /**
     * Throws exception with status 404.
     * @param e - NotFoundExeption
     * @return
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound(NotFoundException e) {
        return ErrorDTO.fromException(e).withStatus(HttpStatus.NOT_FOUND.name());
    }
    
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequest(BadRequestException e) {
        return ErrorDTO.fromException(e).withStatus(HttpStatus.BAD_REQUEST.name());
    }
}
