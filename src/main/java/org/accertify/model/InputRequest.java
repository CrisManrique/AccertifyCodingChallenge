package org.accertify.model;


import javax.validation.constraints.NotNull;

public class InputRequest {

    @NotNull(message = "Word parameter can not be null")
    private String word;
    private int recordFrom;
    private int recordTo;


    public InputRequest() {
    }

    public int getRecordFrom() {
        return recordFrom;
    }

    public void setRecordFrom(int recordFrom) {
        this.recordFrom = recordFrom;
    }

    public int getRecordTo() {
        return recordTo;
    }

    public void setRecordTo(int recordTo) {
        this.recordTo = recordTo;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
