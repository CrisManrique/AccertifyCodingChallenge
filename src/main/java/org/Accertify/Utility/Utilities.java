package org.Accertify.Utility;

import org.Accertify.Model.InputRequest;
import reactor.core.publisher.Mono;

import javax.validation.ValidationException;

public class Utilities {

    public static Mono<InputRequest> validateRequest(InputRequest inputRequest){
            if(inputRequest.getRecordFrom() > inputRequest.getRecordTo()){
                return Mono.error(new ValidationException("recordFrom field can not be greater than the recordTo field"));
            }
            else {
                return Mono.just(inputRequest);
            }
    }
}
