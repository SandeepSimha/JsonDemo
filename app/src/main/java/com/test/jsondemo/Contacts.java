package com.test.jsondemo;

public class Contacts {
    private String id;

    private Phone phone;

    private String address;

    private String email;

    private String name;

    private String gender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
//        return "ClassPojo [id = " + id + ", phone = " + phone + ", address = " + address + ", email = " + email + ", name = " + name + ", gender = " + gender + "]";
        return "Name : " + getName() + "\n" + "Email: " + getEmail();
    }
}