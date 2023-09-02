package com.sternitc.genericapi.transform.converter;

import com.sternitc.genericapi.transform.domain.JsonTypes;

public class ValueConverterNoConversion implements ValueConverter {

    private final ConverterDetails details;

    public ValueConverterNoConversion(JsonTypes from, JsonTypes to, String name) {
        this.details = new ConverterDetails(from, to, name);
    }

    @Override
    public ConverterDetails getDetails() {
        return this.details;
    }

    @Override
    public <V> V convert(Object from, Class<V> clazz) {
        return clazz.cast(from);
    }
}
