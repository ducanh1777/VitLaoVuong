package com.vitlaovuong.model;

import java.sql.Timestamp;

public class Notification {
    private int id;
    private int userId;
    private String roleTarget;
    private String title;
    private String message;
    private boolean isRead;
    private Timestamp createdAt;

    private String link;

    public Notification() {
    }

    public Notification(int id, int userId, String roleTarget, String title, String message, boolean isRead,
            Timestamp createdAt, String link) {
        this.id = id;
        this.userId = userId;
        this.roleTarget = roleTarget;
        this.title = title;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
        this.link = link;
    }

    // Constructor for backward compatibility (optional but good)
    public Notification(int id, int userId, String roleTarget, String title, String message, boolean isRead,
            Timestamp createdAt) {
        this(id, userId, roleTarget, title, message, isRead, createdAt, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRoleTarget() {
        return roleTarget;
    }

    public void setRoleTarget(String roleTarget) {
        this.roleTarget = roleTarget;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
