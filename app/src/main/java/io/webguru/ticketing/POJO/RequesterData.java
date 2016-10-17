package io.webguru.ticketing.POJO;

import java.io.Serializable;

/**
 * Created by yatin on 15/10/16.
 */

public class RequesterData implements Serializable {
    private String issue;
    private String priority;
    private String location;
    private String shop;
    private String site;
    private UserInfo userInfo;

    public RequesterData(){

    }

    public RequesterData(String issue, String priority, String location, String shop, String site, UserInfo userInfo){
        this.issue = issue;
        this.priority = priority;
        this.location = location;
        this.site = site;
        this.shop = shop;
        this.userInfo = userInfo;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
