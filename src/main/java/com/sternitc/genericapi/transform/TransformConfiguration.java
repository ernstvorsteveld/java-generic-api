package com.sternitc.genericapi.transform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sternitc.genericapi.domain.Company;
import com.sternitc.genericapi.transform.converter.ValueConverter;
import com.sternitc.genericapi.transform.converter.ValueConverterNoConversion;
import com.sternitc.genericapi.transform.dao.TransformerLoader;
import com.sternitc.genericapi.transform.dao.TransformerLoaderImpl;
import com.sternitc.genericapi.transform.domain.JsonTypes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class TransformConfiguration {

    @Bean
    public Transformer<Company> companyTransformer(Map<ConverterBeanNames, ValueConverter> converters) {
        return new CompanyTransformer(converters);
    }

    @Bean
    public TransformerLoader transformerLoader() {
        return new TransformerLoaderImpl();
    }

    @Bean
    public Mapper mapper(ObjectMapper objectMapper) {
        return new Mapper(objectMapper);
    }

    @Bean
    public Map<ConverterBeanNames, ValueConverter> converters() {
        return new HashMap<ConverterBeanNames, ValueConverter>(
                Map.ofEntries(
                        new AbstractMap.SimpleEntry<ConverterBeanNames, ValueConverter>(ConverterBeanNames.String2String, getConverter(JsonTypes.String, "String to String converter")),
                        new AbstractMap.SimpleEntry<ConverterBeanNames, ValueConverter>(ConverterBeanNames.Integer2Integer, getConverter(JsonTypes.Number_Integer, "Integer to Integer converter")),
                        new AbstractMap.SimpleEntry<ConverterBeanNames, ValueConverter>(ConverterBeanNames.Double2Double, getConverter(JsonTypes.Number_Double, "Double to Double converter")),
                        new AbstractMap.SimpleEntry<ConverterBeanNames, ValueConverter>(ConverterBeanNames.Float2Float, getConverter(JsonTypes.Number_Float, "Float to Float converter")),
                        new AbstractMap.SimpleEntry<ConverterBeanNames, ValueConverter>(ConverterBeanNames.Boolean2Boolean, getConverter(JsonTypes.Boolean, "Boolean to Boolean converter"))
                )
        );
    }

    private ValueConverter getConverter(JsonTypes type, String name) {
        return new ValueConverterNoConversion(type, type, name);
    }
}
