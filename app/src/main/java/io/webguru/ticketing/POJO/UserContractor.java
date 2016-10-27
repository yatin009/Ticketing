package io.webguru.ticketing.POJO;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yatin on 23/10/16.
 */

public class UserContractor {

    private String companyName;
    private String name;
    private int contactNumber;
    private String email;

    public UserContractor(){

    }

    public UserContractor(String companyName, String name, int contactNumber, String email){
        this.companyName = companyName;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email =email;
    }

    public UserContractor(boolean defaultUser){
        this.companyName = "XYZ";
        this.name = "asd";
        this.email = "qwe@gmail.com";
        this.contactNumber = 123123;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("companyName", companyName);
        result.put("name", name);
        result.put("contactNumber", contactNumber);
        result.put("email", email);

        return result;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
