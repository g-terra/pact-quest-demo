package dev.terralab.blog.examples.pactquestdemo.exception;

public class InvalidStatusTransitionException extends RuntimeException {
    public InvalidStatusTransitionException(String string) {
        super(string);
    }
}
