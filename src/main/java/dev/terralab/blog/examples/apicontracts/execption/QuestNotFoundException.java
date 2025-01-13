package dev.terralab.blog.examples.apicontracts.execption;

public class QuestNotFoundException extends RuntimeException {
    public QuestNotFoundException(String string) {
        super(string);
    }
}
