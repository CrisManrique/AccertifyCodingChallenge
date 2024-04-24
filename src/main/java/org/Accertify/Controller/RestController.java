package org.Accertify.Controller;

import org.Accertify.Model.InputRequest;
import org.Accertify.Model.ResponseBody;
import org.Accertify.Repository.WordRepository;
import org.Accertify.Handler.WordHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import javax.validation.Valid;
import javax.validation.ValidationException;


@ComponentScan("org.Accertify")
@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final WordHandler wordHandler;

    public RestController(WordHandler wordHandler) {
        this.wordHandler = wordHandler;
    }

    @GetMapping("/listAllWords")
    public Mono<ResponseBody> listAllWords(@Valid @RequestBody InputRequest inputRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException("Error invalid request body: parameter \"word\" is either null or invalid");
        }
        else{
            return wordHandler.getWords(inputRequest);
        }

    }

    @ExceptionHandler(RuntimeException.class)
    public Mono<String> handleRunTimeException(RuntimeException exception){
        return Mono.just(exception.getMessage());
    }
}
