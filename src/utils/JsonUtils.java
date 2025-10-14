package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.Map;

public class JsonUtils<E> {


    public static Map<String, String> createMapFromJson(String json) {

        ObjectMapper mapper = new ObjectMapper();

        try {

            Map<String, String> map = mapper.readValue(json, Map.class);
//            map.forEach((k, v) -> System.out.println(k + ": " + v));
            return map;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <E> String createJsonStringFromObject(E object) {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(object); // jsonString
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
