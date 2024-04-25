package org.accertify.utility;

import org.accertify.exceptions.AccertifyRuntimeException;
import org.accertify.model.PaginationRequest;
import reactor.core.publisher.Mono;

public class Validator {

    public static Mono<PaginationRequest> validate(PaginationRequest paginationRequest){
        if(paginationRequest.getRecordFrom() > paginationRequest.getRecordTo()){
            throw new AccertifyRuntimeException("Invalid page range. recordTo always has to less than recordTo");
        }
        else {
            return Mono.just(paginationRequest);
        }
    }
}
