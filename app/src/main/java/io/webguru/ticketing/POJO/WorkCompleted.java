package io.webguru.ticketing.POJO;

import java.io.Serializable;

/**
 * Created by yatin on 28/10/16.
 */

public class WorkCompleted implements Serializable{
    private String dateTime;
    private String note;
    private double finalLabourCost;
    private double finalPartsCost;
    private double finalOthersCost;
    private double finalTotalCost;

    public WorkCompleted(){

    }

    public WorkCompleted(String dateTime, String note, double finalLabourCost, double finalPartsCost,
                         double finalOthersCost, double finalTotalCost){
        this.dateTime = dateTime;
        this.note = note;
        this.finalLabourCost = finalLabourCost;
        this.finalPartsCost = finalPartsCost;
        this.finalOthersCost = finalOthersCost;
        this.finalTotalCost = finalTotalCost;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getFinalLabourCost() {
        return finalLabourCost;
    }

    public void setFinalLabourCost(double finalLabourCost) {
        this.finalLabourCost = finalLabourCost;
    }

    public double getFinalPartsCost() {
        return finalPartsCost;
    }

    public void setFinalPartsCost(double finalPartsCost) {
        this.finalPartsCost = finalPartsCost;
    }

    public double getFinalOthersCost() {
        return finalOthersCost;
    }

    public void setFinalOthersCost(double finalOthersCost) {
        this.finalOthersCost = finalOthersCost;
    }

    public double getFinalTotalCost() {
        return finalTotalCost;
    }

    public void setFinalTotalCost(double finalTotalCost) {
        this.finalTotalCost = finalTotalCost;
    }
}
