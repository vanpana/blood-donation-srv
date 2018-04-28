package com.cyberschnitzel.Domain.Entities;

public class Donation extends Entity {
    public enum DonationStatus {
        Collected(0), Testing(1), Approved(2), Declined(3);

        private int statusID;

        DonationStatus(int statusid) {
            this.statusID = statusid;
        }

        public int getStatusID() {
            return this.statusID;
        }

        public static DonationStatus getByStatusID(int id) {
            switch (id) {
                case 0:
                    return Collected;
                case 1:
                    return Testing;
                case 2:
                    return Approved;
                default:
                    return Declined;
            }
        }
    }

    private String cnp;
    private double quantity;
    private DonationStatus status;
    private int bloodID;
    private int locationid;

    public int getLocationid() {
        return locationid;
    }

    public void setLocationid(int locationid) {
        this.locationid = locationid;
    }

    public Donation(String cnp, double quantity, int status, int bloodID) {
        this.cnp = cnp;
        this.quantity = quantity;
        this.status = DonationStatus.getByStatusID(status);
        this.bloodID = bloodID;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public DonationStatus getStatus() {
        return status;
    }

    public void setStatus(DonationStatus status) {
        this.status = status;
    }

    public int getBloodID() {
        return bloodID;
    }

    public void setBloodID(int bloodID) {
        this.bloodID = bloodID;
    }
}
