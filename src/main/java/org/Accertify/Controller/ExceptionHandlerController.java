package org.Accertify.Controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ExceptionHandlerController {
    @ExceptionHandler(RuntimeException.class)
    public Mono<String> handleRunTimeException(RuntimeException exception){
        return Mono.just(exception.getMessage());
    }
}
