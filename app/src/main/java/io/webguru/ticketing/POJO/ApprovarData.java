package io.webguru.ticketing.POJO;

import java.io.Serializable;

/**
 * Created by yatin on 25/10/16.
 */

public class ApprovarData implements Serializable{
    private UserInfo userInfo;
    private String approvedDateTime;
    private String signatureImage;
    private String note;

    public ApprovarData(){

    }

    public ApprovarData(UserInfo userInfo, String approvedDateTime, String signatureImage, String note){
        this.userInfo = userInfo;
        this.approvedDateTime = approvedDateTime;
        this.signatureImage = signatureImage;
        this.note = note;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getApprovedDateTime() {
        return approvedDateTime;
    }

    public void setApprovedDateTime(String approvedDateTime) {
        this.approvedDateTime = approvedDateTime;
    }

    public String getSignatureImage() {
        return signatureImage;
    }

    public void setSignatureImage(String signatureImage) {
        this.signatureImage = signatureImage;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
