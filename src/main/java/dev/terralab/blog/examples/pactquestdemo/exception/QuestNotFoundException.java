package dev.terralab.blog.examples.pactquestdemo.exception;

public class QuestNotFoundException extends RuntimeException {
    public QuestNotFoundException(String string) {
        super(string);
    }
}
