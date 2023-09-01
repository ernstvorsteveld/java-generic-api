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
import java.util.stream.Stream;

public class CompanyTransformer implements Transformer<Company> {

    @Override
    public Company transformAdd(TransformerSpecification specification, byte[] source) {
        Preparer preparer = new Preparer(source);
        Collection<AddOperation> operations =
                specification.getTransformers().stream()
                        .map(preparer::prepare)
                        .map(AddOperationBuilder::from)
                        .toList();
        return patchCompany(operations);
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

    public static class AddOperationBuilder {
        public static AddOperation from(TransformPreparation preparation) {
            return new AddOperation(
                    JsonPointer.of(preparation.transformer.toPath().path()),
                    Objects.requireNonNull(getNode(preparation)));
        }

        private static JsonNode getNode(TransformPreparation transformPreparation) {
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
                default -> throw new RuntimeException("Not yet implemented");
            }
        }
    }

    private Company patchCompany(Collection<AddOperation> operations) {
        try {
            JsonPatch patch = Mapper.MAPPER.convertValue(operations, JsonPatch.class);
            JsonNode patched = patch.apply(Mapper.MAPPER.createObjectNode());
            return Mapper.MAPPER.treeToValue(patched, Company.class);
        } catch (JsonProcessingException | JsonPatchException e) {
            throw new RuntimeException(e);
        }
    }

}
