package com.test.jsondemo;

import java.util.List;

public class ContactsResponse {

    private List<Contacts> contacts;

    public List<Contacts> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contacts> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "ClassPojo [contacts = " + contacts + "]";
    }

}
