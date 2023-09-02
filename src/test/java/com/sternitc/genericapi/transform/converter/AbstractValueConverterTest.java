package com.sternitc.genericapi.transform.converter;

import com.sternitc.genericapi.transform.domain.JsonTypes;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class AbstractValueConverterTest {

    static Stream<Arguments> valuesNoConversion() {
        return Stream.of(
                Arguments.of(JsonTypes.String, "30", "String to String converter"),
                Arguments.of(JsonTypes.Number_Integer, 30, "Integer to Integer converter"),
                Arguments.of(JsonTypes.Number_Double, 30.0d, "Double to Double converter"),
                Arguments.of(JsonTypes.Number_Float, 30f, "Float to Float converter"),
                Arguments.of(JsonTypes.Boolean, true, "Boolean to Boolean converter"),
                Arguments.of(JsonTypes.Boolean, false, "Boolean to Boolean converter")
        );
    }

    public ValueConverter getConverter(JsonTypes from, JsonTypes to, String name) {
        if (from.equals(to)) {
            return new ValueConverterNoConversion(from, to, name);
        }
        throw new RuntimeException("Not yet implemented");
    }
}
