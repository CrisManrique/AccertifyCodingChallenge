package org.Accertify.Model;

import java.util.List;
import java.util.Map;

public class ResponseBody {
    private Map<String, List<String>> recordMap;

    public ResponseBody() {
    }

    public ResponseBody(Map<String, List<String>> recordMap) {
        this.recordMap = recordMap;
    }

    public Map<String, List<String>> getRecordMap() {
        return recordMap;
    }

    public void setRecordMap(Map<String, List<String>> recordMap) {
        this.recordMap = recordMap;
    }
}
