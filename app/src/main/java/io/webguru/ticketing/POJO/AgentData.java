package io.webguru.ticketing.POJO;

import java.io.Serializable;

/**
 * Created by yatin on 16/10/16.
 */

public class AgentData implements Serializable{
    private String scope;
    private String contractor;
    private int contractorId;
    private String contractorNote;
    private String contractorAssignedDate;
    private String approverName;
    private int approverId;
    private String requestApprovalDate;
    private UserInfo userInfo;

    public AgentData(){

    }

    public AgentData(String scope, String contractor, int contractorId, String contractorNote,
                     String contractorAssignedDate, UserInfo userInfo){
        this.scope = scope;
        this.contractor = contractor;
        this.contractorId = contractorId;
        this.contractorNote = contractorNote;
        this.contractorAssignedDate = contractorAssignedDate;
        this.userInfo = userInfo;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public int getContractorId() {
        return contractorId;
    }

    public void setContractorId(int contractorId) {
        this.contractorId = contractorId;
    }

    public String getContractorNote() {
        return contractorNote;
    }

    public void setContractorNote(String contractorNote) {
        this.contractorNote = contractorNote;
    }

    public String getContractorAssignedDate() {
        return contractorAssignedDate;
    }

    public void setContractorAssignedDate(String contractorAssignedDate) {
        this.contractorAssignedDate = contractorAssignedDate;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
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
