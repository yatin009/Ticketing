package io.webguru.ticketing.POJO;

import java.io.Serializable;
import java.security.SecureRandom;

/**
 * Created by yatin on 29/10/16.
 */

public class AssignedContractor implements Serializable{
    private String contractor;
    private int contractorId;
    private String contractorNote;
    private String contractorAssignedDate;

    public AssignedContractor(){}

    public AssignedContractor( String contractor, int contractorId, String contractorNote,
                               String contractorAssignedDate){
        this.contractor = contractor;
        this.contractorId = contractorId;
        this.contractorNote = contractorNote;
        this.contractorAssignedDate = contractorAssignedDate;
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
}
