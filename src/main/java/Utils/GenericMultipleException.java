package Utils;

import java.util.List;

public class GenericMultipleException extends RuntimeException {
    public GenericMultipleException(List<RuntimeException> exceptionsList) {
        for(RuntimeException e: exceptionsList){
            System.out.println(e.getMessage());
        }
        if(!exceptionsList.isEmpty())
           throw exceptionsList.get(0);
    }

}
