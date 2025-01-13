package dev.terralab.blog.examples.apicontracts.execption;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String string) {
        super(string);
    }
}
