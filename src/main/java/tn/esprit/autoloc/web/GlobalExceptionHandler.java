
package tn.esprit.autoloc.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

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
}
