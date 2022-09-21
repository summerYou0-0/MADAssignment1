package com.example.foodutan;

public class loginData {
    private static int nextId = 0;
    private final int id;
    private String email;
    private String username;
    private String password;

    public loginData(int newID, String newEmail, String newUserName, String newPassword) {
        this.id = newID;
        this.email = newEmail;
        this.username = newUserName;
        this.password = newPassword;
        nextId = id + 1;
    }

    public loginData(String newEmail, String newUserName, String newPassword) {
        this(nextId, newEmail, newUserName, newPassword);
        nextId++;
    }
    public int getID() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
