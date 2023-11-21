package Exceptions;

/**
 * Is thrown during parsing, if the format of a given clause is invalid.
 */
public class InvalidClauseException extends RuntimeException {

    public InvalidClauseException(String msg) {
        super(msg);
    }
}
