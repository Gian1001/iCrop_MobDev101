package com.example.icrop_project;

import java.io.Serializable;

public class CropData implements Serializable {

    String CropType, dateHarvest, datePlanted, dateReportSubmitted, reportID, userID;

    public String getUserID() {
        return userID;
    }

    public String getReportID() {
        return reportID;
    }

    public String getCropType() {
        return CropType;
    }

    public String getDateHarvest() {
        return dateHarvest;
    }

    public String getDatePlanted() {
        return datePlanted;
    }

    public String getDateReportSubmitted() {
        return dateReportSubmitted;
    }
}
