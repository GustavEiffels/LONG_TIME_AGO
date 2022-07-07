package com.example.supertest.Controller;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttp;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;


@RestController
@RequestMapping("name")
@Slf4j
public class TESTController
{
    @GetMapping()
    public String getOpportunity() throws IOException
    {
        String url = "https://sobetec4-dev-ed.my.salesforce.com/services/data/v53.0/sobjects/Opportunity/describe";
        String accessToken = "00D5i000001w419!ARgAQHcYDe5mWyJFqsmE5T63nJ8X43rUiNa9tt5Y2tFIy2t4KzQ8fKFT8T_cr20b6xW_yC9LSO3giJXuI7LHvcjx2FHF4veN";
        RestTemplate restTemplate  = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+accessToken);

        HttpEntity entity = new HttpEntity(headers);



        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        log.info("Access Complete ");

        return response.toString();
    }

    @PostMapping()
    public String createNewAccessToken()
    {

        // Consumer_id -> Consumer
        String client_id = "&client_id="+"3MVG9wt4IL4O5wvLnOs7iYYPnY.fVbU7WzsKV73hA.8mX7BbJWtm0dEuYJ5eDuAACJvs2Moju7RqMrLsZC1wc";

        // Consumer_secret
        String client_secret = "&client_secret="+"1E02B246D8700A16DA08FD3F267431C1B2C3566AF99B2F01B986D339871BFAB5";

        // refresh token
        String refreshToken = "&refresh_token="+"5Aep861ryecz0qkv5xziqwcTPx1fn2NPRscOeh7WpgQ29F_1RX0S8GlmIUYp_NIkm.4gnoQIo1v9PnemJDHysPI";

        //Complete Body
        String requestText = "grant_type=refresh_token"+client_id+client_secret+refreshToken;

        String url = "https://login.salesforce.com/services/oauth2/token";

        RestTemplate restTemplate  = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/x-www-form-urlencoded");

        HttpEntity<String> entity = new HttpEntity<String>(requestText,headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return response.toString();
    }




}
