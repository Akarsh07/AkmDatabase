package com.akm.database.model;

public class ContactModel {

	private int id;
    private String contactId;
    private String stagingId;

    public ContactModel(){

    }
	public ContactModel(int id, String contactId, String stagingId) {
        this.id = id;
		this.contactId = contactId;
		this.stagingId = stagingId;
	}

	public int getId() { return id; }

	public void setId(int id) {
		this.id = id;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getStagingId() {
		return stagingId;
	}

	public void setStagingId(String stagingId) {
		this.stagingId = stagingId;
	}
}
