
package tn.esprit.autoloc.web;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> gererErreursValidation(
            MethodArgumentNotValidException exception) {

        Map<String, String> erreurs = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(erreur ->
                erreurs.put(
                        erreur.getField(),
                        erreur.getDefaultMessage()
                )
        );

        return erreurs;
    }

    @ExceptionHandler({
            NoSuchElementException.class,
            EntityNotFoundException.class
    })
    public ResponseEntity<Map<String, String>> gererRessourceIntrouvable(
            RuntimeException exception) {
        return reponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<Map<String, String>> gererRequeteInvalide(
            RuntimeException exception) {
        return reponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> gererConflit(
            DataIntegrityViolationException exception) {
        return reponse(HttpStatus.CONFLICT,
                "L'opération viole une contrainte de données.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> gererErreurInattendue(
            Exception exception) {
        LOGGER.error("Erreur inattendue lors du traitement de la requête",
                exception);
        return reponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Une erreur interne est survenue.");
    }

    private ResponseEntity<Map<String, String>> reponse(
            HttpStatus status, String message) {
        Map<String, String> erreur = new HashMap<>();
        erreur.put("status", String.valueOf(status.value()));
        erreur.put("message", message == null ? status.getReasonPhrase() : message);
        return ResponseEntity.status(status).body(erreur);
    }
}
