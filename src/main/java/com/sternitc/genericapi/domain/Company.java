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
    private Address address;
}
