package com.sternitc.genericapi.transform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sternitc.genericapi.domain.Company;
import com.sternitc.genericapi.transform.dao.TransformerLoader;
import com.sternitc.genericapi.transform.dao.TransformerLoaderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransformConfiguration {

    @Bean
    public Transformer<Company> companyTransformer() {
        return new CompanyTransformer();
    }

    @Bean
    public TransformerLoader transformerLoader() {
        return new TransformerLoaderImpl();
    }

    @Bean
    public Mapper mapper(ObjectMapper objectMapper) {
        return new Mapper(objectMapper);
    }
}
