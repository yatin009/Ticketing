package io.webguru.ticketing.POJO;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yatin on 22/10/16.
 */

public class Analytics {
    private String date;
    private int total_count;
    private int incoming_count;
    private int dispatched_count;
    private int approval_count;
    private int ongoing_count;
    private int high_count;

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("total_count", total_count);
        result.put("incoming_count", incoming_count);
        result.put("dispatched_count", dispatched_count);
        result.put("approval_count", approval_count);
        result.put("ongoing_count", ongoing_count);
        result.put("high_count", high_count);

        return result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getIncoming_count() {
        return incoming_count;
    }

    public void setIncoming_count(int incoming_count) {
        this.incoming_count = incoming_count;
    }

    public int getDispatched_count() {
        return dispatched_count;
    }

    public void setDispatched_count(int dispatched_count) {
        this.dispatched_count = dispatched_count;
    }

    public int getApproval_count() {
        return approval_count;
    }

    public void setApproval_count(int approval_count) {
        this.approval_count = approval_count;
    }

    public int getOngoing_count() {
        return ongoing_count;
    }

    public void setOngoing_count(int ongoing_count) {
        this.ongoing_count = ongoing_count;
    }

    public int getHigh_count() {
        return high_count;
    }

    public void setHigh_count(int high_count) {
        this.high_count = high_count;
    }
}
