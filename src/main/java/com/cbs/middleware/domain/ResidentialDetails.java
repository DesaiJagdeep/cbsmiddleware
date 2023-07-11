package com.cbs.middleware.domain;

public class ResidentialDetails {

    /*
     * {
     * "residentialVillage": "058136",
     * "residentialAddress": "Test Address",
     * "residentialPincode": "567876"
     * }
     */

    private String residentialVillage;
    private String residentialAddress;
    private String residentialPincode;

    //GETTERS AND SETTERS
    public String getResidentialVillage() {
        return residentialVillage;
    }

    public void setResidentialVillage(String residentialVillage) {
        this.residentialVillage = residentialVillage;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getResidentialPincode() {
        return residentialPincode;
    }

    public void setResidentialPincode(String residentialPincode) {
        this.residentialPincode = residentialPincode;
    }
}
