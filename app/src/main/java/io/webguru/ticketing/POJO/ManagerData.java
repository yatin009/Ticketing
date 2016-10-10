package io.webguru.ticketing.POJO;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import io.webguru.ticketing.Global.GlobalFunctions;

/**
 * Created by yatin on 27/09/16.
 */

public class ManagerData implements Serializable{
    private String ticketNumber;
    private String fieldRequestKey;
    private String dateCreated;
    private String type;
    private String area;
    private String accountID;
    private String description;
    private String vendor;
    private String requesterName;
    private String requesterPhone;
    private String requesterEmail;
    private String equipment;
    private String status;
    private String dateQuoted;
    private String quotePriceTotal;
    private String finalPrice;
    private String varianceToQuote;
    private String quotedParts;
    private String quotedServices;
    private String quotedOthers;
    private String dateSubmittedForApproval;
    private String dateApproved;
    private String financialGroup;
    private String fmrGroup;
    private String approvedBy;
    private String comments;
    private String onDemandRequestID;
    private String taxable;
    private String hst;
    private String reportStatus;
    private String reportPrice;
    private String reportDate;
    private String filter;

    //Copy of FieldAgent Data
    private FieldAgentData fieldAgentData;

    public ManagerData(){
    }

    public ManagerData(FieldAgentData fieldAgentData, UserInfo userInfo, String fieldRequestKey){
        this.fieldRequestKey = fieldRequestKey;
        this.description = fieldAgentData.getDescription();
        this.requesterName = userInfo.getFirstname() +" " + userInfo.getLastname();
        this.requesterEmail = userInfo.getEmail();
        this.requesterPhone = userInfo.getNumber();
        this.status = fieldAgentData.getStatus();
        this.fieldAgentData = fieldAgentData;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ticketNumber", ticketNumber);
        result.put("fieldRequestKey", fieldRequestKey);
        result.put("dateCreated", dateCreated);
        result.put("type", type);
        result.put("area", area);
        result.put("accountID", accountID);
        result.put("description", description);
        result.put("vendor", vendor);
        result.put("requesterName", requesterName);
        result.put("requesterPhone", requesterPhone);
        result.put("requesterEmail", requesterEmail);
        result.put("equipment", equipment);
        result.put("status", status);
        result.put("dateQuoted", dateQuoted);
        result.put("quotePriceTotal", quotePriceTotal);
        result.put("finalPrice", finalPrice);
        result.put("varianceToQuote", varianceToQuote);
        result.put("quotedParts", quotedParts);
        result.put("quotedServices", quotedServices);
        result.put("quotedOthers", quotedOthers);
        result.put("dateSubmittedForApproval", dateSubmittedForApproval);
        result.put("dateApproved", dateApproved);
        result.put("financialGroup", financialGroup);
        result.put("fmrGroup", fmrGroup);
        result.put("approvedBy", approvedBy);
        result.put("comments", comments);
        result.put("onDemandRequestID", onDemandRequestID);
        result.put("taxable", taxable);
        result.put("hst", hst);
        result.put("reportStatus", reportStatus);
        result.put("reportPrice", reportPrice);
        result.put("reportDate", reportDate);
        result.put("filter", filter);
        result.put("fieldAgentData", fieldAgentData);

        return result;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getRequesterPhone() {
        return requesterPhone;
    }

    public void setRequesterPhone(String requesterPhone) {
        this.requesterPhone = requesterPhone;
    }

    public String getRequesterEmail() {
        return requesterEmail;
    }

    public void setRequesterEmail(String requesterEmail) {
        this.requesterEmail = requesterEmail;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateQuoted() {
        return dateQuoted;
    }

    public void setDateQuoted(String dateQuoted) {
        this.dateQuoted = dateQuoted;
    }

    public String getQuotePriceTotal() {
        return quotePriceTotal;
    }

    public void setQuotePriceTotal(String quotePriceTotal) {
        this.quotePriceTotal = quotePriceTotal;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getVarianceToQuote() {
        return varianceToQuote;
    }

    public void setVarianceToQuote(String varianceToQuote) {
        this.varianceToQuote = varianceToQuote;
    }

    public String getQuotedParts() {
        return quotedParts;
    }

    public void setQuotedParts(String quotedParts) {
        this.quotedParts = quotedParts;
    }

    public String getQuotedServices() {
        return quotedServices;
    }

    public void setQuotedServices(String quotedServices) {
        this.quotedServices = quotedServices;
    }

    public String getQuotedOthers() {
        return quotedOthers;
    }

    public void setQuotedOthers(String quotedOthers) {
        this.quotedOthers = quotedOthers;
    }

    public String getDateSubmittedForApproval() {
        return dateSubmittedForApproval;
    }

    public void setDateSubmittedForApproval(String dateSubmittedForApproval) {
        this.dateSubmittedForApproval = dateSubmittedForApproval;
    }

    public String getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(String dateApproved) {
        this.dateApproved = dateApproved;
    }

    public String getFinancialGroup() {
        return financialGroup;
    }

    public void setFinancialGroup(String financialGroup) {
        this.financialGroup = financialGroup;
    }

    public String getFmrGroup() {
        return fmrGroup;
    }

    public void setFmrGroup(String fmrGroup) {
        this.fmrGroup = fmrGroup;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getOnDemandRequestID() {
        return onDemandRequestID;
    }

    public void setOnDemandRequestID(String onDemandRequestID) {
        this.onDemandRequestID = onDemandRequestID;
    }

    public String getTaxable() {
        return taxable;
    }

    public void setTaxable(String taxable) {
        this.taxable = taxable;
    }

    public String getHst() {
        return hst;
    }

    public void setHst(String hst) {
        this.hst = hst;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getReportPrice() {
        return reportPrice;
    }

    public void setReportPrice(String reportPrice) {
        this.reportPrice = reportPrice;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getFieldRequestKey() {
        return fieldRequestKey;
    }

    public void setFieldRequestKey(String fieldRequestKey) {
        this.fieldRequestKey = fieldRequestKey;
    }

    public FieldAgentData getFieldAgentData() {
        return fieldAgentData;
    }

    public void setFieldAgentData(FieldAgentData fieldAgentData) {
        this.fieldAgentData = fieldAgentData;
    }
}
