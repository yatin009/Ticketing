package io.webguru.ticketing.POJO;

import java.io.Serializable;

/**
 * Created by yatin on 29/10/16.
 */

public class WorkRating implements Serializable{
    private String dateTime;
    private int rating;
    private String note;

    public WorkRating(){

    }

    public WorkRating(String dateTime, int rating, String note){
        this.dateTime = dateTime;
        this.rating = rating;
        this.note = note;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
