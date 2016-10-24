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
    private String contactNumber;
    private String email;

    public UserContractor(){

    }

    public UserContractor(boolean defaultUser){
        this.companyName = "XYZ";
        this.name = "asd";
        this.email = "qwe@gmail.com";
        this.contactNumber = "123123";
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
