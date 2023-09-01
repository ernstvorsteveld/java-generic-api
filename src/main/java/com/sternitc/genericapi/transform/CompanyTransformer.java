package com.sternitc.genericapi.transform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DecimalNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.AddOperation;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.jayway.jsonpath.JsonPath;
import com.sternitc.genericapi.domain.Company;
import com.sternitc.genericapi.transform.domain.AttributeTransformer;
import com.sternitc.genericapi.transform.domain.TransformerSpecification;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

public class CompanyTransformer implements Transformer<Company> {

    @Override
    public Company transformAdd(TransformerSpecification specification, byte[] source) {
        Collection<TransformPreparation> values = prepare(specification, source);

        Collection<AddOperation> operations = values.stream().
                map(t -> new AddOperation(
                        JsonPointer.of(t.transformer.toPath().path()),
                        Objects.requireNonNull(getNode(t)))).toList();

        try {
            Company target = new Company();
            JsonPatch patch = Mapper.MAPPER.readValue(Mapper.MAPPER.writeValueAsString(operations), JsonPatch.class);
            JsonNode patched = patch.apply(Mapper.MAPPER.convertValue(target, JsonNode.class));
            return Mapper.MAPPER.treeToValue(patched, Company.class);
        } catch (JsonProcessingException | JsonPatchException e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<TransformPreparation> prepare(
            TransformerSpecification specification,
            byte[] bytes) {
        Preparer preparer = new Preparer(bytes);
        return specification.getTransformers().stream().map(preparer::prepare).toList();
    }

    private JsonNode getNode(TransformPreparation transformPreparation) {
        if (transformPreparation.value == null) {
            return null;
        }

        switch (transformPreparation.transformer.fromPath().type()) {
            case String -> {
                return new TextNode((String) transformPreparation.value);
            }
            case Number -> {
                return new DecimalNode((BigDecimal) transformPreparation.value);
            }
            case Boolean -> {
                if ((Boolean) transformPreparation.value) {
                    return BooleanNode.getTrue();
                } else {
                    return BooleanNode.getFalse();
                }
            }
            default -> {
                throw new RuntimeException("Not yet implemented");
            }
        }
    }

    private record TransformPreparation(AttributeTransformer transformer, Object value) {
    }

    private static class Preparer {
        private final String jsonString;

        public Preparer(byte[] bytes) {
            jsonString = new String(bytes);
        }

        TransformPreparation prepare(AttributeTransformer transformer) {
            return new TransformPreparation(
                    transformer,
                    JsonPath.read(jsonString, transformer.fromPath().path()));
        }
    }
}
