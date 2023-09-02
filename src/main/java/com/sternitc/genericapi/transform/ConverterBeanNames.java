package com.sternitc.genericapi.transform;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ConverterBeanNames {

    @JsonProperty("String2String")
    String2String("value.converter.name.string.2.string"),
    @JsonProperty("Integer2Integer")
    Integer2Integer("value.converter.name.integer.to.integer"),
    @JsonProperty("Float2Float")
    Float2Float("value.converter.name.float.to.float"),
    @JsonProperty("Double2Double")
    Double2Double("value.converter.name.double.to.double"),
    @JsonProperty("Boolean2Boolean")
    Boolean2Boolean("value.converter.name.boolean.to.boolean");

    public final String name;

    ConverterBeanNames(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
