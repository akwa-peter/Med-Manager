package com.example.rad5.med_manager.Help_Classes;

/**
 * Created by akwa on 4/4/18.
 * This class collects the medication details entered by the user
 */

public class Medication {

    private String name;
    private String description;
    private String frequency;
    private String startDate;
    private String endDate;

    public Medication(String name, String description, String frequency, String startDate, String endDate){
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
