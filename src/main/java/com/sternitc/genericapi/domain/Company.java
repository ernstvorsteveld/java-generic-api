package com.sternitc.genericapi.domain;

import lombok.*;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Company extends Transformable {
    private String id;
    private String name;
    private String commercialName;
    private int numberOfEmployees;
    // Initialize the nested object, since jsonPatch only allows for adding to existing objects
    private LegalEntity legalEntity = new LegalEntity();
    private Address address;
}
