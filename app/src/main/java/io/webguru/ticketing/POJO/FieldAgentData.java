package io.webguru.ticketing.POJO;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yatin on 25/09/16.
 */

public class FieldAgentData implements Serializable {
    private String approved;
    private String dateTime;
    private String problem;
    private String priority;
    private String photoUrl;
    private String photoPath;
    private String location;
    private String ticketNumber;

    public FieldAgentData() {
    }

    public FieldAgentData(String approved, String dateTime, String priority, String photoUrl, String problem, String photoPath, String location, String ticketNumber) {
        this.approved = approved;
        this.dateTime = dateTime;
        this.priority = priority;
        this.problem = problem;
        this.photoUrl = photoUrl;
        this.photoPath = photoPath;
        this.location = location;
        this.ticketNumber = ticketNumber;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
        result.put("approved", "false");
        result.put("dateTime", dateTime);
        result.put("problem", problem);
        result.put("priority", priority);
        result.put("photoUrl", photoUrl);
        result.put("photoPath", photoPath);
        result.put("location", location);
        result.put("ticketNumber", ticketNumber);

        return result;
    }
}

