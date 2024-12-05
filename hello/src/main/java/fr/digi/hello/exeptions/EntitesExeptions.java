package fr.digi.hello.exeptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EntitesExeptions {

    @ExceptionHandler({VilleExeption.class})
    public ResponseEntity<String> villeHandler(VilleExeption e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DepartementExeption.class)
    public ResponseEntity<String> departementHandler(DepartementExeption e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
