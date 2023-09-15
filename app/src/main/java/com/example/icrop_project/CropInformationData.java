package com.example.icrop_project;

import java.io.Serializable;

public class CropInformationData implements Serializable {

    String description, end_date, name, soil_type, start_date, sunlight, temperature, type,watering;

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getName() {
        return name;
    }

    public String getSoil_type() {
        return soil_type;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getSunlight() {
        return sunlight;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWatering() {
        return watering;
    }
}
