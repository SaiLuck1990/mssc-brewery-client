package com.beer.msscbreweryclient.web.config;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

//@Component
public class NIORestTemplateCustomizer implements RestTemplateCustomizer {

    private final Integer connectTimeout;
    private final Integer ioThreadCount;
    private final Integer soTimeout;
    private final Integer defaultMaxperRoute;
    private final Integer maxTotal;

    public NIORestTemplateCustomizer(@Value("${sfg.nio.connecttimeout}") Integer connectTimeout,
                                     @Value("${sfg.nio.iothreadcount}") Integer ioThreadCount,
                                     @Value("${sfg.nio.sotimeout}") Integer soTimeout,
                                     @Value("${sfg.nio.defaultmaxperroute}") Integer defaultMaxperRoute,
                                     @Value("${sfg.nio.maxtotal}") Integer maxTotal) {
        this.connectTimeout = connectTimeout;
        this.ioThreadCount = ioThreadCount;
        this.soTimeout = soTimeout;
        this.defaultMaxperRoute = defaultMaxperRoute;
        this.maxTotal = maxTotal;
    }


    public ClientHttpRequestFactory clientHttpRequestFactory() throws IOReactorException {
        final DefaultConnectingIOReactor ioreactor = new DefaultConnectingIOReactor(IOReactorConfig.custom().
                setConnectTimeout(connectTimeout).
                setIoThreadCount(ioThreadCount).
                setSoTimeout(soTimeout).
                build());

        final PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(ioreactor);
        connectionManager.setDefaultMaxPerRoute(defaultMaxperRoute);
        connectionManager.setMaxTotal(maxTotal);

        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        return new HttpComponentsAsyncClientHttpRequestFactory(httpAsyncClient);

    }


    @Override
    public void customize(RestTemplate restTemplate) {
        try {
            restTemplate.setRequestFactory(clientHttpRequestFactory());
        }catch (IOReactorException e){
            e.printStackTrace();
        }
    }
}
