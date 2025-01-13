package dev.terralab.blog.examples.pactquestdemo.execption;

public class QuestNotFoundException extends RuntimeException {
    public QuestNotFoundException(String string) {
        super(string);
    }
}
