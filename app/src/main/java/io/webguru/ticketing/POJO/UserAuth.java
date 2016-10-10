package io.webguru.ticketing.POJO;

/**
 * Created by yatin on 25/09/16.
 */

public class UserAuth {
    private String username;
    private String password;
    private String username_password;
    private String userid;

    public UserAuth(){

    }

    public String getUsername_password() {
        return username_password;
    }

    public void setUsername_password(String username_password) {
        this.username_password = username_password;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
