package com.java.developer.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JobService {

  private final RestTemplate restTemplate;
  private final ObjectMapper mapper;

  @Autowired
  public JobService(RestTemplate restTemplate, ObjectMapper mapper) {
    this.restTemplate = restTemplate;
    this.mapper = mapper;
  }

  //http://dev3.dansmultipro.co.id/api/recruitment/positions.json
  public JsonNode getJobs() throws JsonProcessingException {
    final ResponseEntity<String> result = restTemplate.getForEntity(
        "http://dev3.dansmultipro.co.id/api/recruitment/positions.json",
        String.class
    );
    return mapper.readTree(result.getBody());
  }

  //http://dev3.dansmultipro.co.id/api/recruitment/positions.json
  public JsonNode getJobDtl(String id) throws JsonProcessingException {
    final ResponseEntity<String> result = restTemplate.getForEntity(
        String.format("http://dev3.dansmultipro.co.id/api/recruitment/positions/%s", id),
        String.class
    );
    return mapper.readTree(result.getBody());
  }


}
