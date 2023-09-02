package com.sternitc.genericapi.transform;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConverterBeanNamesTest {

    @Test
    public void should_have_name() {
        assertThat(ConverterBeanNames.String2String.getName()).isEqualTo("value.converter.name.string.2.string");
    }

}