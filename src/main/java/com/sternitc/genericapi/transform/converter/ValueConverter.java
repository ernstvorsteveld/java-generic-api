package com.sternitc.genericapi.transform.converter;

import com.sternitc.genericapi.transform.domain.JsonTypes;

public interface ValueConverter {

    record ConverterDetails(JsonTypes from, JsonTypes to, String displayName) {
    }

    ConverterDetails getDetails();

    <V> V convert(Object from, Class<V> clazz);
}
