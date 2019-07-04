package com.akm.database.model;

public class AccountModel {

    private int status;
    private String userID;
    private String context;
    public AccountModel(){}
    public AccountModel(int status, String userID, String context) {
        this.status = status;
        this.userID = userID;
        this.context = context;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
