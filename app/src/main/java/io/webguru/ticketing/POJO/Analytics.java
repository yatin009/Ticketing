package io.webguru.ticketing.POJO;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import io.webguru.ticketing.Global.GlobalFunctions;

/**
 * Created by yatin on 22/10/16.
 */

public class Analytics implements Serializable{
    private String analyticsDate;
    private int totalCount;
    private int incomingCount;
    private int dispatchedCount;
    private int approvalCount;
    private int approvedCount;
    private int highCount;
    private int mediumCount;
    private int lowCount;

    public Analytics() {
    }

    public Analytics(boolean isDefault) {
        this.analyticsDate = GlobalFunctions.getTodaysDateFormatted();
        this.totalCount = 0;
        this.incomingCount = 0;
        this.dispatchedCount = 0;
        this.approvalCount = 0;
        this.approvedCount = 0;
        this.highCount = 0;
        this.mediumCount = 0;
        this.lowCount = 0;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("analyticsDate", analyticsDate);
        result.put("total_count", totalCount);
        result.put("incoming_count", incomingCount);
        result.put("dispatched_count", dispatchedCount);
        result.put("approval_count", approvalCount);
        result.put("approved_count", approvedCount);
        result.put("high_count", highCount);
        result.put("medium_count", mediumCount);
        result.put("low_count", lowCount);

        return result;
    }

    public String getAnalyticsDate() {
        return analyticsDate;
    }

    public void setAnalyticsDate(String analyticsDate) {
        this.analyticsDate = analyticsDate;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getIncomingCount() {
        return incomingCount;
    }

    public void setIncomingCount(int incomingCount) {
        this.incomingCount = incomingCount;
    }

    public int getDispatchedCount() {
        return dispatchedCount;
    }

    public void setDispatchedCount(int dispatchedCount) {
        this.dispatchedCount = dispatchedCount;
    }

    public int getApprovalCount() {
        return approvalCount;
    }

    public void setApprovalCount(int approvalCount) {
        this.approvalCount = approvalCount;
    }

    public int getApprovedCount() {
        return approvedCount;
    }

    public void setApprovedCount(int approvedCount) {
        this.approvedCount = approvedCount;
    }

    public int getHighCount() {
        return highCount;
    }

    public void setHighCount(int highCount) {
        this.highCount = highCount;
    }

    public int getMediumCount() {
        return mediumCount;
    }

    public void setMediumCount(int mediumCount) {
        this.mediumCount = mediumCount;
    }

    public int getLowCount() {
        return lowCount;
    }

    public void setLowCount(int lowCount) {
        this.lowCount = lowCount;
    }
}
