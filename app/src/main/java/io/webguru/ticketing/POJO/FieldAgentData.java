package io.webguru.ticketing.POJO;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yatin on 25/09/16.
 */

public class FieldAgentData {
    private String approved;
    private String date;
    private String time;
    private String problem;
    private String priority;
    private String photoUrl;
    private String photoPath;
    private String location;

    public FieldAgentData() {
    }

    public FieldAgentData(String approved, String date, String time, String priority, String photoUrl, String problem, String photoPath, String location) {
        this.approved = approved;
        this.date = date;
        this.time = time;
        this.priority = priority;
        this.problem = problem;
        this.photoUrl = photoUrl;
        this.photoPath = photoPath;
        this.location = location;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("approved", "false");
        result.put("date", date);
        result.put("time", time);
        result.put("problem", problem);
        result.put("priority", priority);
        result.put("photoUrl", photoUrl);
        result.put("photoPath", photoPath);
        result.put("location", location);

        return result;
    }
}

