package dev.terralab.blog.examples.apicontracts.execption;

public class InvalidStatusTransitionException extends RuntimeException {
    public InvalidStatusTransitionException(String string) {
        super(string);
    }
}
