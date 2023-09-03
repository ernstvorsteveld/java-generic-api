package com.sternitc.genericapi.transform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sternitc.genericapi.domain.Company;
import com.sternitc.genericapi.domain.LegalEntity;
import com.sternitc.genericapi.transform.domain.TransformerSpecification;
import lombok.Builder;
import lombok.Data;

import java.util.List;

public abstract class AbstractCompanyTransformerTest extends AbstractTest {

    public static final String TEST_UUID = "test-uuid";
    public static final String TEST_NAME = "My test name";
    public static final String COMMERCIAL_NAME = "commercial name";
    public static final int EMPLOYEE_COUNT = 2;

    record TransformerInputFiles(String filename) {
    }

    static List<TransformerInputFiles> transformerInputFiles;

    static {
        transformerInputFiles = List.of(
                new TransformerInputFiles("com/sternitc/genericapi/transform/transformer-specification-1.json"),
                new TransformerInputFiles("com/sternitc/genericapi/transform/transformer-specification-2.json")
        );
    }

    enum TransformerFiles {

        SIMPLE(0),
        WITH_SUB_OBJECT(1);
        private final int index;

        TransformerFiles(int i) {
            index = i;
        }

        int getIndex() {
            return index;
        }
    }

    @Data
    @Builder
    static class APICompany {
        private String uuid;
        private String companyName;
        private APICompanyDetails apiCompanyDetails;
    }

    @Data
    @Builder
    static class APICompanyDetails {
        private int employeeCount;
        private String commercialName;
        private boolean accepted;
    }

    TransformerSpecification getTransformerSpec(TransformerFiles file) {
        return read(transformerInputFiles.get(file.getIndex()).filename(), TransformerSpecification.class);
    }

    Company getExpected() {
        return Company.builder()
                .id(TEST_UUID)
                .name(TEST_NAME)
                .commercialName(COMMERCIAL_NAME)
                .numberOfEmployees(EMPLOYEE_COUNT)
                .legalEntity(LegalEntity.builder()
                        .valid(false)
                        .build())
                .build();
    }

    byte[] getAPICompany() throws JsonProcessingException {
        APICompany result = APICompany.builder()
                .companyName(TEST_NAME)
                .uuid(TEST_UUID)
                .apiCompanyDetails(APICompanyDetails.builder()
                        .commercialName(COMMERCIAL_NAME)
                        .employeeCount(EMPLOYEE_COUNT)
                        .accepted(false)
                        .build()
                )
                .build();
        return mapper.writeValueAsBytes(result);
    }
}
