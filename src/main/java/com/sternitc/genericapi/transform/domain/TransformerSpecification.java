package com.sternitc.genericapi.transform.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sternitc.genericapi.domain.TreasurUpModels;
import com.sternitc.genericapi.transform.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TransformerSpecification {

    private TreasurUpModels model;
    private Collection<AttributeTransformer> transformers;

    private TransformerSpecification() {
    }

    public TransformerSpecification(TreasurUpModels model) {
        this.model = model;
        this.transformers = new HashSet<>();
    }

    public TransformerSpecification(Collection<AttributeTransformer> transformers) {
        this.transformers = transformers;
    }

    public void add(AttributeTransformer specification) {
        transformers.add(specification);
    }

    public void replace(AttributeTransformer specification) {
        Collection<AttributeTransformer> replaced = new ArrayList<AttributeTransformer>();
        transformers.stream().filter(s -> !s.id().equals(specification.id())).map(s -> replaced.add(s));
        replaced.add(specification);
        this.transformers = replaced;
    }

    public Collection<AttributeTransformer> getTransformers() {
        return transformers;
    }

    public byte[] bytes() {
        try {
            return Mapper.MAPPER.writeValueAsBytes(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String string() {
        try {
            return Mapper.MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
