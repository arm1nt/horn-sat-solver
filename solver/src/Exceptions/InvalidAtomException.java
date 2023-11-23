package Exceptions;

/**
 * Is thrown if an atom is invalid
 */
public class InvalidAtomException extends RuntimeException{

    public InvalidAtomException(String msg) {
        super(msg);
    }
}
