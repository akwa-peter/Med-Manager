package com.example.rad5.med_manager.Help_Classes;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by akwa on 4/4/18.
 * This class collects the medication details entered by the user
 */

public class Medication implements Serializable{

    private String description;
    private String frequency;
    private String startDate;
    private String endDate;

    public Medication(){}

    public Medication(String description, String endDate, String frequency, String startDate){
        this.description = description;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("description", description);
        result.put("frequency", frequency);
        result.put("startDate", startDate);
        result.put("endDate", endDate);
        return result;
    }
}
