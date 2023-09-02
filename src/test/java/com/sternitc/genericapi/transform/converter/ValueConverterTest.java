package com.sternitc.genericapi.transform.converter;

import com.sternitc.genericapi.transform.domain.JsonTypes;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class ValueConverterTest extends AbstractValueConverterTest {

    @ParameterizedTest
    @MethodSource("valuesNoConversion")
    public void should_convert_string_without_converting(JsonTypes type, Object value, String name) {
        ValueConverter converter = getConverter(type, type, name);

        Class<?> clazz = JsonTypes.getClassName(type);
        var actual = converter.convert(value, JsonTypes.getClassName(type));
        assertThat(clazz.cast(actual)).isEqualTo(clazz.cast(value));
        assertThat(converter.getDetails().displayName()).isEqualTo(name);
        assertThat(converter.getDetails().from()).isEqualTo(type);
        assertThat(converter.getDetails().to()).isEqualTo(type);
    }
}