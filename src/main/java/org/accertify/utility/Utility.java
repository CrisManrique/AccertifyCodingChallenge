package org.accertify.utility;

import org.accertify.exceptions.AccertifyRuntimeException;
import org.accertify.model.InputRequest;
import org.accertify.model.PaginationRequest;
import reactor.core.publisher.Mono;

public class Utility {

    public static Mono<PaginationRequest> validate(PaginationRequest paginationRequest){
        if(paginationRequest == null){return Mono.empty();}
        if(paginationRequest.getRecordFrom() > paginationRequest.getRecordTo()){
            throw new AccertifyRuntimeException("Invalid page range. recordTo always has to less than recordTo");
        }
        else {
            return Mono.just(paginationRequest);
        }
    }
}
