package org.accertify.utility;

import org.accertify.exceptions.AccertifyRuntimeException;
import org.accertify.model.InputRequest;

public class Utility {

    public static boolean validate(InputRequest inputRequest){
        if(inputRequest.getRecordFrom() > inputRequest.getRecordTo()){
            throw new AccertifyRuntimeException("Invalid page range. recordTo always has to less than recordTo");
        }
        else {
            return true;
        }
    }
}
