package org.accertify.model;

import java.util.List;

public class TransformedRequest {
    private String word;
    private List<String> subWords;

    public TransformedRequest(String word, List<String> subWords) {
        this.word = word;
        this.subWords = subWords;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getSubWords() {
        return subWords;
    }

    public void setSubWords(List<String> subWords) {
        this.subWords = subWords;
    }
}
