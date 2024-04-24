package org.Accertify.Model;


import javax.validation.constraints.NotNull;

public class InputRequest {

    @NotNull(message = "Word parameter can not be null")
    private String word;

    private int recordFrom;
    private int recordTo;

    public InputRequest(String word, int recordFrom, int recordTo) {
        this.word = word;
        this.recordFrom = recordFrom;
        this.recordTo = recordTo;
    }

    public InputRequest() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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
}
