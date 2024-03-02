package com.TransactionHub.exception;

import com.TransactionHub.dto.jwt.JwtResponse;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<JwtResponse> catchUsernameNotFoundException(UsernameNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new JwtResponse(HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler ResponseEntity<JwtResponse> catchJwtException(JwtException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new JwtResponse(HttpStatus.FORBIDDEN, e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler ResponseEntity<JwtResponse> catchPhoneNumberExistsException(PhoneNumberException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new JwtResponse(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler ResponseEntity<JwtResponse> catchEmailException(EmailException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new JwtResponse(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler ResponseEntity<JwtResponse> catchTransferException(TransferException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new JwtResponse(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
