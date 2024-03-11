package be.technobel.backfermedubeaulieu.pl.controllers.controllerAdvice;

import be.technobel.backfermedubeaulieu.pl.config.exceptions.DuplicateUserException;
import be.technobel.backfermedubeaulieu.pl.config.exceptions.EntityAlreadyExistsException;
import be.technobel.backfermedubeaulieu.pl.models.dtos.ErrorDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>(new ErrorDTO(e.getMessage(), ""), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorDTO> handleMultipartException(MultipartException e) {
        return new ResponseEntity<>(new ErrorDTO(e.getMessage(), ""), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationExceptions(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ErrorDTO(e.getMessage(), ""), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new ResponseEntity<>(new ErrorDTO(e.getMessage(), ""), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDTO> handleAuthenticationServiceException(AuthenticationException e) {
        return new ResponseEntity<>(new ErrorDTO(e.getMessage(), ""), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(new ErrorDTO(e.getMessage(), ""), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(new ErrorDTO(e.getMessage(), ""), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorDTO> handleDuplicateUserException(DuplicateUserException e) {
        return new ResponseEntity<>(new ErrorDTO(e.getMessage(), ""), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleEntityAlreadyExistsException(EntityAlreadyExistsException e) {
        return new ResponseEntity<>(new ErrorDTO(e.getMessage(), ""), HttpStatus.NOT_ACCEPTABLE);
    }

}
