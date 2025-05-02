package com.example.RealConnect.properties.domain;

public enum Property_type {
    Apartment("Apartment"),
    Villa("Villa"),
    Rent("Rent"),
    Officetel("Officetel"),
    Commercial("Commercial"),
    Land("Land");

    private String value;

    Property_type(String value) {this.value = value;}
}
