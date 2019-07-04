package com.akm.database.model;

public class ExtensionModel {
    private String context;
	private int phoneContactId;

    public ExtensionModel(){}

	public ExtensionModel(String context, int phoneContactId) {
        this.context = context;
		this.phoneContactId = phoneContactId;
	}

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

	public int getPhoneContactId() {
		return phoneContactId;
	}

	public void setPhoneContactId(int phoneContactId) {
		this.phoneContactId = phoneContactId;
	}
}
