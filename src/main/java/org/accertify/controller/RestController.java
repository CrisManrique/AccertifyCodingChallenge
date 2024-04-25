package org.accertify.controller;

import org.accertify.model.InputRequest;
import org.accertify.model.PaginationRequest;
import org.accertify.model.ResponseBody;
import org.accertify.handler.WordHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import javax.validation.Valid;
import javax.validation.ValidationException;


@ComponentScan("org.accertify")
@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final WordHandler wordHandler;

    public RestController(WordHandler wordHandler) {
        this.wordHandler = wordHandler;
    }

    @GetMapping("/getWordAndSubwords")
    public Mono<ResponseBody> getWordAndSubwords(@Valid @RequestBody InputRequest inputRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException("Error invalid request body: parameter \"word\" is either null or invalid");
        }
        else{
            return wordHandler.getWordAndSubwords(inputRequest);
        }
    }

    @GetMapping("/listAllWords")
    public Mono<ResponseBody> getAllWords(@RequestBody(required = false) PaginationRequest paginationRequest) {
        if(paginationRequest == null || (paginationRequest.getRecordFrom() == null && paginationRequest.getRecordTo() == null)) {
            paginationRequest = null;
        }
        return wordHandler.getAllWords(paginationRequest);
    }

    @PostMapping("/addWord")
    public Mono<ResponseBody> addWord(@Valid @RequestBody InputRequest inputRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException("Error invalid request body: parameter \"word\" is either null or invalid");
        }
        else{
            return wordHandler.addWord(inputRequest);
        }
    }

    @DeleteMapping("/deleteWord")
    public Mono<ResponseBody> deleteWord(@Valid @RequestBody InputRequest inputRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException("Error invalid request body: parameter \"word\" is either null or invalid");
        }
        else{
            return wordHandler.deleteWord(inputRequest);
        }
    }

    @GetMapping("/findSubWords")
    public Mono<ResponseBody> findSubWords(@Valid @RequestBody InputRequest inputRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException("Error invalid request body: parameter \"word\" is either null or invalid");
        }
        else{
            return wordHandler.findSubWords(inputRequest);
        }
    }

//    @ExceptionHandler(Exception.class)
//    public Mono<String> handleRunTimeException(Exception exception){
//        return Mono.just(exception.getMessage());
//    }
}

