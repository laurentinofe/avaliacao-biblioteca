package com.senac.biblioteca.configs;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerMapper {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handle(EntityNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(404);
        ProblemDetail.setTitle("Entidade não encontrada");
        problemDetail.setDetail(ex.getMessage());

        problemDetail.setProperty("DataHoraErro", LocalDateTime.now());
        return ResponseEntity.of(problemDetail).build();
    }

}
