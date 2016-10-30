package io.webguru.ticketing.POJO;

import java.io.Serializable;

/**
 * Created by yatin on 29/10/16.
 */

public class AssignedApprover implements Serializable{
    private String approverName;
    private int approverId;
    private String requestApprovalDate;

    public AssignedApprover(){}

    public AssignedApprover(String approverName, int approverId, String requestApprovalDate){
        this.approverName = approverName;
        this.approverId = approverId;
        this.requestApprovalDate = requestApprovalDate;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public int getApproverId() {
        return approverId;
    }

    public void setApproverId(int approverId) {
        this.approverId = approverId;
    }

    public String getRequestApprovalDate() {
        return requestApprovalDate;
    }

    public void setRequestApprovalDate(String requestApprovalDate) {
        this.requestApprovalDate = requestApprovalDate;
    }
}
