package com.example.anna.model;

public class User {
    public String username, email, depart;
    public User(){};
    public User(String username, String email, String depart){
        this.username=username;
        this.email  =email;
        this.depart = depart;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getDepart() {
        return depart;
    }
}
