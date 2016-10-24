package io.webguru.ticketing.POJO;

import java.io.Serializable;

/**
 * Created by yatin on 16/10/16.
 */

public class ContractorData implements Serializable{

    private String quotedDateTime;
    private double quotePriceTotal;
    private double finalPrice;
    private double varianceToQuote;
    private double quotedParts;
    private double quotedServices;
    private double quotedLabour;
    private UserInfo userInfo;

    public ContractorData(){

    }

    public ContractorData(String quotedDateTime, Double quotePriceTotal, Double finalPrice, Double varianceToQuote,
                   Double quotedParts, Double quotedServices, Double quotedLabour, UserInfo userInfo){
        this.quotedDateTime = quotedDateTime;
        this.quotePriceTotal = quotePriceTotal;
        this.finalPrice = finalPrice;
        this.varianceToQuote = varianceToQuote;
        this.quotedParts = quotedParts;
        this.quotedServices = quotedServices;
        this.quotedLabour = quotedLabour;
        this.userInfo = userInfo;
    }

    public String getQuotedDateTime() {
        return quotedDateTime;
    }

    public void setQuotedDateTime(String quotedDateTime) {
        this.quotedDateTime = quotedDateTime;
    }

    public double getQuotePriceTotal() {
        return quotePriceTotal;
    }

    public void setQuotePriceTotal(double quotePriceTotal) {
        this.quotePriceTotal = quotePriceTotal;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public double getVarianceToQuote() {
        return varianceToQuote;
    }

    public void setVarianceToQuote(double varianceToQuote) {
        this.varianceToQuote = varianceToQuote;
    }

    public double getQuotedParts() {
        return quotedParts;
    }

    public void setQuotedParts(double quotedParts) {
        this.quotedParts = quotedParts;
    }

    public double getQuotedServices() {
        return quotedServices;
    }

    public void setQuotedServices(double quotedServices) {
        this.quotedServices = quotedServices;
    }

    public double getQuotedLabour() {
        return quotedLabour;
    }

    public void setQuotedLabour(double quotedLabour) {
        this.quotedLabour = quotedLabour;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
