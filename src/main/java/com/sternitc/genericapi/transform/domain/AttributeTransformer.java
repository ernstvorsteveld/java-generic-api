package com.sternitc.genericapi.transform.domain;

public record AttributeTransformer(
        String id,
        String order,
        PathSpecification fromPath,
        PathSpecification toPath,
        ValueConverterSpecification converter,
        Boolean mandatory) {
}
