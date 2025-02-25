package com.wonderDev.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonderDev.dto.CountryResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@ApplicationScoped
public class CountryClient {

    @Inject
    @ConfigProperty(name = "restcountries.api.url")
    String apiUrl;

    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson para deserialización

    public String getDemonymByCountryCode(String countryCode) {
        try {
            var client = ClientBuilder.newClient();
            var response = client.target(apiUrl + countryCode)
                    .request(MediaType.APPLICATION_JSON)
                    .get(String.class);

            List<CountryResponse> countries = objectMapper.readValue(response, new TypeReference<>() {
            });

            if (!countries.isEmpty()) {
                return countries.get(0).getDemonym();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "Unknown";
    }
}
