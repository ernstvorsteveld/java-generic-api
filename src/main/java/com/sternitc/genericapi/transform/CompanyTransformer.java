package com.sternitc.genericapi.transform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.AddOperation;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.jayway.jsonpath.JsonPath;
import com.sternitc.genericapi.domain.Company;
import com.sternitc.genericapi.transform.converter.ValueConverter;
import com.sternitc.genericapi.transform.domain.AttributeTransformer;
import com.sternitc.genericapi.transform.domain.JsonTypes;
import com.sternitc.genericapi.transform.domain.TransformerSpecification;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
public class CompanyTransformer implements Transformer<Company> {

    private Map<ConverterBeanNames, ValueConverter> converters;

    @Override
    public Company transformAdd(TransformerSpecification specification, byte[] source) {
        Preparer preparer = new Preparer(source);
        Collection<AddOperation> operations =
                specification.getTransformers().stream()
                        .map(preparer::prepare)
                        .map(p -> new AddOperationBuilder(converters).with(p).build())
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
        private final Map<ConverterBeanNames, ValueConverter> converters;
        private TransformPreparation preparation;

        public AddOperationBuilder(Map<ConverterBeanNames, ValueConverter> converters) {
            this.converters = converters;
        }

        public AddOperationBuilder with(TransformPreparation preparation) {
            this.preparation = preparation;
            return this;
        }

        public AddOperation build() {
            return new AddOperation(
                    JsonPointer.of(preparation.transformer.toPath().path()),
                    Objects.requireNonNull(getNode(preparation, converters)));
        }

        private static JsonNode getNode(
                TransformPreparation transformPreparation,
                Map<ConverterBeanNames, ValueConverter> converters) {
            if (transformPreparation.value == null) {
                return null;
            }

            ValueConverter converter = converters.get(transformPreparation.transformer.converter().bean());
            var type = transformPreparation.transformer.fromPath().type();
            var value = converter.convert(transformPreparation.value, JsonTypes.getClassName(type));
            switch (type) {
                case String -> {
                    return new TextNode((String) value);
                }
                case Number_Integer -> {
                    return new IntNode((Integer) value);
                }
                case Number_Double -> {
                    return new DoubleNode((Double) value);
                }
                case Number_Float -> {
                    return new FloatNode((Float) value);
                }
                case Boolean -> {
                    if ((Boolean) value) {
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
