package com.cyberschnitzel.Domain.Entities;


import java.util.Date;

public class Blood extends Entity {
    private BloodType bloodType;
    private Date receivedDate;

    public Blood(){}
    public Blood(String bloodType) {
        this.bloodType = BloodType.getByString(bloodType);
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }


    public String getType(){return "Blood";}
}
