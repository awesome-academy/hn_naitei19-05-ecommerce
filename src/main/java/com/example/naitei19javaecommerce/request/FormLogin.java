package com.example.naitei19javaecommerce.request;

public class FormLogin {
    private String username;

    private String password;

    public FormLogin() {
    }

    public FormLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
