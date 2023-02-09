package com.example.social;

public class User {
    String UserUID;
    String email;
    String name;

    public User(String userUID, String email, String name) {
        UserUID = userUID;
        this.email = email;
        this.name = name;
    }

    public User() {

    }

    public String getUserUID() {
        return UserUID;
    }

    public void setUserUID(String userUID) {
        UserUID = userUID;
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
}
