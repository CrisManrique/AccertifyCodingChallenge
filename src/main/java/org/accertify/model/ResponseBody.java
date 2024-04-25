package org.accertify.model;

import java.util.List;
import java.util.Map;

public class ResponseBody {
    private Map<String, List<String>> recordMap;
    private String successMessage;
    public ResponseBody() {
    }

    public ResponseBody(Map<String, List<String>> recordMap) {
        this.recordMap = recordMap;
    }

    public ResponseBody(String successMessage) {
        this.successMessage = successMessage;
    }

    public ResponseBody(Map<String, List<String>> recordMap, String successMessage) {
        this.recordMap = recordMap;
        this.successMessage = successMessage;
    }

    public Map<String, List<String>> getRecordMap() {
        return recordMap;
    }

    public void setRecordMap(Map<String, List<String>> recordMap) {
        this.recordMap = recordMap;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
