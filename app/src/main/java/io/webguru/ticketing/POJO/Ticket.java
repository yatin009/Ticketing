package io.webguru.ticketing.POJO;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yatin on 15/10/16.
 */

public class Ticket implements Serializable{

    private String ticketNumber;
    private String status;
    private String priority;
    private int requesterId;
    private int agentId;
    private int approverId;
    private int contractorId;
    private String dateTime;
    private RequesterData requester;
    private AgentData agentData;
    private ContractorData contractorData;

    private boolean isDetailsShown;

    public Ticket(){

    }

    public Ticket(String ticketNumber, String status, int requesterId, int agentId, int approverId, int contractorId, String dateTime, RequesterData requesterData){
        this.ticketNumber = ticketNumber;
        this.status = status;
        this.priority = requesterData.getPriority();
        this.requesterId =requesterId;
        this.agentId = agentId;
        this.approverId = approverId;
        this.contractorId = contractorId;
        this.dateTime = dateTime;
        this.requester = requesterData;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ticketNumber", ticketNumber);
        result.put("status", status);
        result.put("priority", priority);
        result.put("requesterId", requesterId);
        result.put("agentId", agentId);
        result.put("approverId", approverId);
        result.put("contractorId", contractorId);
        result.put("dateTime", dateTime);
        result.put("requester", requester);
        result.put("agentData", agentData);
        result.put("contractorData", contractorData);

        return result;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
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

    public int getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public int getApproverId() {
        return approverId;
    }

    public void setApproverId(int approverId) {
        this.approverId = approverId;
    }

    public int getContractorId() {
        return contractorId;
    }

    public void setContractorId(int contractorId) {
        this.contractorId = contractorId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public RequesterData getRequester() {
        return requester;
    }

    public void setRequester(RequesterData requester) {
        this.requester = requester;
    }

    public AgentData getAgentData() {
        return agentData;
    }

    public void setAgentData(AgentData agentData) {
        this.agentData = agentData;
    }

    public ContractorData getContractorData() {
        return contractorData;
    }

    public void setContractorData(ContractorData contractorData) {
        this.contractorData = contractorData;
    }

    public boolean isDetailsShown() {
        return isDetailsShown;
    }

    public void setDetailsShown(boolean detailsShown) {
        isDetailsShown = detailsShown;
    }
}
