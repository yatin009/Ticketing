package io.webguru.ticketing.POJO;

import java.io.Serializable;

/**
 * Created by yatin on 16/10/16.
 */

public class AgentData implements Serializable{
    private String scope;


    private UserInfo userInfo;

    public AgentData(){

    }

    public AgentData(String scope, UserInfo userInfo){
        this.scope = scope;
        this.userInfo = userInfo;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

}
