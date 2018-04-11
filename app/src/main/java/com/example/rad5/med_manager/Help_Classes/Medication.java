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

    private String name;
    private String description;
    private String quantity;
    private String frequency;
    private String startDate;
    private String endDate;
    private int zMedicationMonth;

    public Medication(){}

    public Medication(String description, String endDate,
                      String frequency, String name, String quantity,
                      String startDate, int zMedicationMonth){
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.zMedicationMonth = zMedicationMonth;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getQuantity() {
        return quantity;
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

    public int getzMedicationMonth() {
        return zMedicationMonth;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("description", description);
        result.put("endDate", endDate);
        result.put("frequency", frequency);
        result.put("name", name);
        result.put("quantity", quantity);
        result.put("startDate", startDate);
        result.put("zMedicationMonth", zMedicationMonth);
        return result;
    }
}
