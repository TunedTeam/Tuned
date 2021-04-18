package com.example.tuned.models;

public class Result {
    private String resultId;
    private int resultImageUrl;
    private String resultName;

    public Result (String resultId, int resultImageUrl, String resultName) {
        this.resultId = resultId;
        this.resultImageUrl = resultImageUrl;
        this.resultName = resultName;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public int getResultImageUrl() {
        return resultImageUrl;
    }

    public void setResultImageUrl(int resultImageUrl) {
        this.resultImageUrl = resultImageUrl;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }
}
