package com.beer.msscbreweryclient.web.client;

import com.beer.msscbreweryclient.web.dto.BeerDTO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(value="sfg.brewery" , ignoreUnknownFields = false)
public class BreweryClient {

    public final String BEER_PATH_V1 = "/api/v1/beer/";
    private final RestTemplate restTemplate;

    private String apiHost;

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public BeerDTO getBeerById(UUID beerId) {
        return  restTemplate.getForObject(apiHost+BEER_PATH_V1 + beerId.toString(), BeerDTO.class);
    }

    public URI saveNewBeer(BeerDTO beerDTO)  {
        return restTemplate.postForLocation(apiHost+BEER_PATH_V1 , beerDTO);
    }

    public void updateBeer(UUID beerId , BeerDTO beerDTO) {
        restTemplate.put(apiHost + BEER_PATH_V1 + "/" + beerId , beerDTO);
    }

    public void deleteBeer(UUID beerId){
        restTemplate.delete(apiHost + BEER_PATH_V1 + "/"+ beerId);
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }
}
