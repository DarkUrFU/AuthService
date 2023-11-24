package com.darkurfu.authservice.restclient;

import lombok.Singular;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component()
public class RestClient {
    RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory());



}
