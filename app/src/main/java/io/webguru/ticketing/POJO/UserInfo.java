package io.webguru.ticketing.POJO;

import java.io.Serializable;

/**
 * Created by yatin on 25/09/16.
 */

public class UserInfo implements Serializable{
    private String firstname;
    private String lastname;
    private String password;
    private String role;
    private String username;
    private String userid;
    private boolean isLoggedin;

    private String email;
    private String number;
    private String photoUrl;

    public UserInfo(){

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public boolean isLoggedin() {
        return isLoggedin;
    }

    public boolean isIsLoggedin() {
        return isLoggedin;
    }

    public void setIsLoggedin(boolean value){
        isLoggedin = value;
    }

    public void setLoggedin(boolean loggedin) {
        isLoggedin = loggedin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
