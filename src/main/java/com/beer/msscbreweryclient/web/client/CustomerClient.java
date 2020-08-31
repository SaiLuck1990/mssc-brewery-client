package com.beer.msscbreweryclient.web.client;

import com.beer.msscbreweryclient.web.dto.CustomerDTO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(value = "sfg.brewery" , ignoreUnknownFields = false)
public class CustomerClient {

    private final String BEER_PATH_V1 = "api/v1/customer/";
    private final RestTemplate restTemplate;
    private String apiHost;


    public CustomerClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public CustomerDTO getCustomerById(UUID customerId){
        return restTemplate.getForObject(apiHost+BEER_PATH_V1+customerId.toString(),CustomerDTO.class);
    }

    public URI saveCustomer(CustomerDTO customerDTO) {
        return restTemplate.postForLocation(apiHost+BEER_PATH_V1 , customerDTO);
    }

    public void updateCustomer(CustomerDTO customerDTO , UUID customerId) {
         restTemplate.put(apiHost+BEER_PATH_V1+customerId.toString(),customerDTO);
    }

    public void deleteCustomer(UUID customerId) {
        restTemplate.delete(apiHost+BEER_PATH_V1+customerId);
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }
}
