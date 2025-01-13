package dev.terralab.blog.examples.pactquestdemo.execption;

public class InvalidStatusTransitionException extends RuntimeException {
    public InvalidStatusTransitionException(String string) {
        super(string);
    }
}
