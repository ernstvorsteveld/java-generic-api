package com.sternitc.genericapi.transform;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Mapper {

    public static ObjectMapper MAPPER;

    public Mapper(ObjectMapper objectMapper) {
        MAPPER = objectMapper;
    }
}
