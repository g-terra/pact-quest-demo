package dev.terralab.blog.examples.pactquestdemo.execption;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String string) {
        super(string);
    }
}
