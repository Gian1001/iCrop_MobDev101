package com.example.icrop_project;

import java.io.Serializable;

public class SoilData implements Serializable {

    String best_crops, common_issues, description, name, nutrient_content, organic_matter_content, ph_range;

    public String getBest_crops() {
        return best_crops;
    }

    public String getCommon_issues() {
        return common_issues;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getNutrient_content() {
        return nutrient_content;
    }

    public String getOrganic_matter_content() {
        return organic_matter_content;
    }

    public String getPh_range() {
        return ph_range;
    }
}
