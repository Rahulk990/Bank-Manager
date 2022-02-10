package com.flock.bankManager.models;

public class Notification {
    private String email;
    private String update;

    public Notification() {
    }

    public Notification(String email, String update) {
        this.email = email;
        this.update = update;
    }

    public String stringify() {
        return "Email: " + email + ", Notification: " + update;
    }
}
