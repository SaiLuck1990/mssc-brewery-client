package com.beer.msscbreweryclient;

import com.beer.msscbreweryclient.web.client.CustomerClient;
import com.beer.msscbreweryclient.web.dto.CustomerDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

@SpringBootTest
public class CustomerClientTest {

    @Autowired
    CustomerClient customerClient;

    @Test
    void testGetCustomer(){
        Assertions.assertNotNull(customerClient.getCustomerById(UUID.randomUUID()));
    }

    @Test
    void testPostCustomer(){
        CustomerDTO customerDTO = CustomerDTO.builder().name("Sai").build();
        URI uri = customerClient.saveCustomer(customerDTO);
        System.out.println(uri);
        Assertions.assertNotNull(uri);
    }

    @Test
    void testUpdateCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder().name("Sai").build();
        customerClient.updateCustomer(customerDTO , UUID.randomUUID());
    }

    @Test
    void testDeleteCustomer() {
        customerClient.deleteCustomer(UUID.randomUUID());
    }
}
