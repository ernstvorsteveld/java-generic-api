package com.sternitc.genericapi.transform;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public abstract class AbstractTest {

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    public <T> T read(String filename, Class<T> clazz) {
        try {
            ClassPathResource resource = new ClassPathResource(filename);
            return mapper.readValue(resource.getFile(), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
