package com.rajith.accordtest.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RestController
@RequestMapping("/api/v1/external")
public class ExternalApiController {

    @GetMapping("/")
    public ResponseEntity<String> callExternalApi() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://www.google.com";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl, String.class);
        return response;
    }
}
