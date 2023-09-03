package com.sternitc.genericapi.transform;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.stream.Stream;

public abstract class AbstractTest {

    ObjectMapper mapper = new ObjectMapper();

    public <T> T read(String filename, Class<T> clazz) {
        try {
            ClassPathResource resource = new ClassPathResource(filename);
            return mapper.readValue(resource.getFile(), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Stream<Arguments> provideFiles() {
        return Stream.of(
                Arguments.of("com/sternitc/genericapi/transform/transformer-specification-test.json", 2),
                Arguments.of("com/sternitc/genericapi/transform/transformer-specification-test2.json", 1));
    }
}
