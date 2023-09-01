package com.sternitc.genericapi.transform.domain;

import com.sternitc.genericapi.transform.domain.PathSpecification;

public record AttributeTransformer(
        String id,
        String order,
        PathSpecification fromPath,
        PathSpecification toPath,
        Boolean mandatory) {
}
