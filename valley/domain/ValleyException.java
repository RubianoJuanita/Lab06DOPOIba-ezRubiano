package domain;

/**
 * Exception to handle errors in the Valley
 * 
 * @author IbañezRubiano
 * @version 1.0
 */
public class ValleyException extends Exception {

    /**
     * Constructor that receives an error message
     * 
     * @param message descriptive error message
     */
    public ValleyException(String message) {
        super(message);
    }
}
