package com.example.domain.model;

import lombok.Value;
import java.util.Map;

@Value
public class Error {
    String code;
    String message;
    Map<String, String> details;
}
