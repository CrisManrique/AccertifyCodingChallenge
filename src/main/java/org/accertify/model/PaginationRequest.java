package org.accertify.model;

public class PaginationRequest {
    private Integer recordFrom;
    private Integer recordTo;

    public PaginationRequest(int recordFrom, int recordTo) {
        this.recordFrom = recordFrom;
        this.recordTo = recordTo;
    }

    public PaginationRequest() {
    }

    public Integer getRecordFrom() {
        return recordFrom;
    }

    public void setRecordFrom(Integer recordFrom) {
        this.recordFrom = recordFrom;
    }

    public Integer getRecordTo() {
        return recordTo;
    }

    public void setRecordTo(Integer recordTo) {
        this.recordTo = recordTo;
    }
}
