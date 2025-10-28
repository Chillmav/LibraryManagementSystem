package utils;

import java.util.Map;

public record PathMethodParams (String method, String path, Map<String, String> queryParams){
}
