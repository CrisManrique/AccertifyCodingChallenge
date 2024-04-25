package org.accertify.model;


import javax.validation.constraints.NotNull;

public class InputRequest {

    @NotNull(message = "Word parameter can not be null")
    private String word;

    public InputRequest(String word) {
        this.word = word;
    }

    public InputRequest() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
