package com.sternitc.genericapi.transform;

import com.sternitc.genericapi.domain.TreasurUpModels;
import com.sternitc.genericapi.transform.domain.AttributeTransformer;
import com.sternitc.genericapi.transform.domain.JsonTypes;
import com.sternitc.genericapi.transform.domain.PathSpecification;
import com.sternitc.genericapi.transform.domain.TransformerSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
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
        TransformerSpecification specification = new TransformerSpecification(TreasurUpModels.Company);
        specification.add(new AttributeTransformer(
                UUID.randomUUID().toString(),
                "10",
                new PathSpecification("/name", JsonTypes.String),
                new PathSpecification("/name", JsonTypes.String),
                true));

        log.info(specification.string());
    }

    @Test
    public void should_wire_objectmapper() {
        assertThat(mapper).isNotNull();
    }

    @Test
    public void should_read_json_file() {
        TransformerSpecification specification = read(
                "com/sternitc/genericapi/transform/transformer-specification-test.json", TransformerSpecification.class);
        assertThat(specification.getTransformers().size()).isEqualTo(2);
    }
}
