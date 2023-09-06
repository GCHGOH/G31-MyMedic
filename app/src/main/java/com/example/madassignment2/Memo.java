package com.example.madassignment2;

public class Memo {
    private String memoId;
    private String userId;
    private String timestamp;
    private String title;
    private String content;

    public Memo(String memoId, String userId, String timestamp, String title, String content) {
        this.memoId = memoId;
        this.userId = userId;
        this.timestamp = timestamp;
        this.title = title;
        this.content = content;
    }

    public String  getMemoId() {
        return memoId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
