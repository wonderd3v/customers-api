package com.wonderDev.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryResponse {

    @JsonProperty("demonyms")
    private Map<String, Map<String, String>> demonyms;

    public String getDemonym() {
        if (demonyms != null && demonyms.containsKey("eng")) {
            return demonyms.get("eng").get("m");
        }
        return "Unknown";
    }
}
