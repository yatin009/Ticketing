package io.webguru.ticketing.POJO;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yatin on 27/09/16.
 */

public class ManagerData implements Serializable{
    private String agentName;
    private String requestDateTime;
    private String problem;
    private String location;
    private String status;
    private String priority;
    private String contractor;
    private String photoUrl;
    private String fieldRequestKey;
    private String ticketNumber;

    public ManagerData(){
    }

    public ManagerData(String agentName, String requestDateTime, String problem, String location, String status, String priority, String contractor, String photoUrl, String fieldRequestKey, String ticketNumber){
        this.agentName = agentName;
        this.requestDateTime = requestDateTime;
        this.problem = problem;
        this.location = location;
        this.status = status;
        this.priority = priority;
        this.contractor = contractor;
        this.photoUrl = photoUrl;
        this.fieldRequestKey = fieldRequestKey;
        this.ticketNumber = ticketNumber;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(String requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getFieldRequestKey() {
        return fieldRequestKey;
    }

    public void setFieldRequestKey(String fieldRequestKey) {
        this.fieldRequestKey = fieldRequestKey;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("agentName", agentName);
        result.put("requestDateTime", requestDateTime);
        result.put("problem", problem);
        result.put("location", location);
        result.put("photoUrl", photoUrl);
        result.put("status", status);
        result.put("priority", priority);
        result.put("ticketNumber", ticketNumber);
        result.put("contractor", contractor);
        result.put("fieldRequestKey", fieldRequestKey);

        return result;
    }
}
