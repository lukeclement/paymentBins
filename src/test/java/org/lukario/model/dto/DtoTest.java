package org.lukario.model.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.lukario.model.Bucket;
import org.lukario.model.Model;
import org.lukario.model.TimeWindow;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DtoTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static Stream<Arguments> dtoProvider() {
        return Stream.of(
                arguments("BucketDto.json", Bucket.createBucket("test bucket", TimeWindow.MONTHLY, 120., TimeWindow.YEARLY))
        );
    }

    @ParameterizedTest
    @MethodSource("dtoProvider")
    void givenAModelICanConvertToDto(String jsonFile, Model<Dto> model) throws IOException {
        Dto dto = model.toDto();
        JsonNode actualJson = MAPPER.valueToTree(dto);
        InputStream inputStream = new ClassPathResource(jsonFile).getInputStream();
        JsonNode expectedJson = MAPPER.readValue(inputStream, JsonNode.class);
        assertEquals(actualJson, expectedJson);
    }
}
