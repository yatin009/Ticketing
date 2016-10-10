package io.webguru.ticketing.POJO;

/**
 * Created by yatin on 09/10/16.
 */

public class ChatData {
    private String text;
    private String name;
    private String role;
    private String userName;
    private String photoUrl;

    public ChatData() {
    }

    public ChatData(String text, String name, String role, String userName, String photoUrl) {
        this.text = text;
        this.name = name;
        this.role = role;
        this.userName = userName;
        this.photoUrl = photoUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
