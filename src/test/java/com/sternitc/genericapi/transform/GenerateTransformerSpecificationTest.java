package com.sternitc.genericapi.transform;

import com.sternitc.genericapi.domain.Models;
import com.sternitc.genericapi.transform.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({TransformConfiguration.class})
@Slf4j
public class GenerateTransformerSpecificationTest extends AbstractTest {

    @Test
    public void should_generate_transformer_specification() {
        TransformerSpecification specification = new TransformerSpecification(Models.Company);
        specification.add(new AttributeTransformer(
                UUID.randomUUID().toString(),
                "10",
                new PathSpecification("name", JsonTypes.String),
                new PathSpecification("name", JsonTypes.String),
                new ValueConverterSpecification(ConverterBeanNames.String2String),
                true));

        log.info(specification.string());
    }

    @Test
    public void should_wire_objectMapper() {
        assertThat(mapper).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("provideFiles")
    public void should_read_json_files(String file, int length) {
        TransformerSpecification specification = read(file, TransformerSpecification.class);
        assertThat(specification.getTransformers().size()).isEqualTo(length);
    }
}
