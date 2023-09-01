package com.sternitc.genericapi.transform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sternitc.genericapi.domain.Company;
import com.sternitc.genericapi.transform.domain.TransformerSpecification;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({TransformConfiguration.class})
class CompanyTransformerTest extends AbstractTest {

    public static final String TEST_UUID = "test-uuid";
    public static final String TEST_NAME = "My test name";

    @Data
    private static class APICompany {
        private String uuid;
        private String companyName;
    }

    @Autowired
    private Transformer<Company> transformer;

    @Test
    public void should_transform_simple_company() throws JsonProcessingException {
        Company result = transformer.transformAdd(getTransformerSpec(), getAPICompany());
        Company expected = getExpected();
        assertThat(result.getName()).isEqualTo(expected.getName());
        assertThat(result.getId()).isEqualTo(expected.getId());
    }

    private TransformerSpecification getTransformerSpec() {
        return read("com/sternitc/genericapi/transform/transformer-specification-1.json",
                TransformerSpecification.class);
    }

    private Company getExpected() {
        return Company.builder().id(TEST_UUID).name(TEST_NAME).build();
    }

    private byte[] getAPICompany() throws JsonProcessingException {
        APICompany result = new APICompany();
        result.setCompanyName(TEST_NAME);
        result.setUuid(TEST_UUID);
        return mapper.writeValueAsBytes(result);
    }

}