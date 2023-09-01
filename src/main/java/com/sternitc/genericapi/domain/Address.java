package com.sternitc.genericapi.domain;

import lombok.Data;

@Data
public class Address {

    private String street;
    private String number;
    private String additional;
    private String postalCode;
    private String city;
    private String country;
}
