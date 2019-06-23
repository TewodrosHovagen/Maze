package utils.exceptions;

import java.util.List;

public class WrongFileFormatMultipleException extends RuntimeException {
    public WrongFileFormatMultipleException(List<RuntimeException> exceptionsList) {
        for(RuntimeException e: exceptionsList){
            System.out.println(e.getMessage());
        }
//        if(!exceptionsList.isEmpty())
//           throw new WrongFileFormatException("Wrong File Format");
    }

}
