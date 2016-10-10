package io.webguru.ticketing.POJO;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yatin on 25/09/16.
 */

public class FieldAgentData implements Serializable {
    private String status;
    private String dateTime;
    private String ticketNumber;

    private String description;
    private String priority;
    private String location;
    private String shop;
    private String site;
    private String scope;
    private String ssrType;

    public FieldAgentData() {
    }

    public FieldAgentData(String status, String dateTime, String ticketNumber, String description, String priority, String location, String shop, String site, String scope, String ssrType) {
        this.status = status;
        this.dateTime = dateTime;
        this.ticketNumber = ticketNumber;

        this.description = description;
        this.priority = priority;
        this.location = location;
        this.shop = shop;
        this.site = site;
        this.scope = scope;
        this.ssrType = ssrType;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("status", status);
        result.put("dateTime", dateTime);
        result.put("ticketNumber", ticketNumber);

        result.put("description", description);
        result.put("priority", priority);
        result.put("location", location);
        result.put("shop", shop);
        result.put("site", site);
        result.put("ssrType", ssrType);

        return result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSsrType() {
        return ssrType;
    }

    public void setSsrType(String ssrType) {
        this.ssrType = ssrType;
    }


}

