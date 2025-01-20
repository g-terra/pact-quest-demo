package dev.terralab.blog.examples.pactquestdemo.exception;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String string) {
        super(string);
    }
}
