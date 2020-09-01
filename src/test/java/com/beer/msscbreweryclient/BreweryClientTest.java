package com.beer.msscbreweryclient;

import com.beer.msscbreweryclient.web.client.BreweryClient;
import com.beer.msscbreweryclient.web.dto.BeerDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

/**
 * This is an integration test and it requires /api/v1/beer
 * to be available in port 8080
 *
 */
@SpringBootTest
public class BreweryClientTest {

    @Autowired
    BreweryClient breweryClient;

    @Test
    void testGetBeerById() {
        BeerDTO beerDTO = breweryClient.getBeerById(UUID.randomUUID());
        Assertions.assertNotNull(beerDTO);
    }

    @Test
    void testSaveBeer() {
        BeerDTO beerDTO = BeerDTO.builder().beerName("Cobra").build();
        URI uri = breweryClient.saveNewBeer(beerDTO);
        Assertions.assertNotNull(uri);
        System.out.println(uri);
    }

    @Test
    void testUpdateBeer() {
        BeerDTO beerDTO = BeerDTO.builder().beerName("Cobra").build();
        breweryClient.updateBeer(UUID.randomUUID() , beerDTO);
    }

    @Test
    void testDeleteBeer() {
        breweryClient.deleteBeer(UUID.randomUUID());
    }
}
