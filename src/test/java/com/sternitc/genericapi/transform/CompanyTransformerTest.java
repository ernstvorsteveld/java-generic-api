package com.sternitc.genericapi.transform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sternitc.genericapi.domain.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({TransformConfiguration.class})
class CompanyTransformerTest extends AbstractCompanyTransformerTest {

    @Autowired
    private Transformer<Company> transformer;

    @Test
    public void should_transform_simple_company() throws JsonProcessingException {
        Company result = transformer.transformAdd(getTransformerSpec(TransformerFiles.SIMPLE), getAPICompany());
        Company expected = getExpected();
        assertThat(result.getName()).isEqualTo(expected.getName());
        assertThat(result.getId()).isEqualTo(expected.getId());
    }

    @Test
    public void should_transform_with_sub_object() throws JsonProcessingException {
        Company result = transformer.transformAdd(getTransformerSpec(TransformerFiles.WITH_SUB_OBJECT), getAPICompany());
        Company expected = getExpected();
        assertThat(result.getName()).isEqualTo(expected.getName());
        assertThat(result.getId()).isEqualTo(expected.getId());
        assertThat(result.getCommercialName()).isEqualTo(expected.getCommercialName());
        assertThat(result.getNumberOfEmployees()).isEqualTo(expected.getNumberOfEmployees());
//        assertThat(result.getLegalEntity().isValid()).isEqualTo(expected.getLegalEntity().isValid());
    }
}