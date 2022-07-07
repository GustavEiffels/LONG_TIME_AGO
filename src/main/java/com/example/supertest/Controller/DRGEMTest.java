package com.example.supertest.Controller;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@RestController
@Slf4j
@RequestMapping("drgem/")
public class DRGEMTest
{
     @PostMapping("createAccount")
    public String createAccount(String name)
     {
         /** Account 를 생성하기 위한 URL*/
         String url = "https://drgem2--sbtdev.sandbox.my.salesforce.com/services/data/v53.0/sobjects/Account";

         /** Access Token */
         String accessToken = "00D9D0000000aTP!ARYAQI4cprQ4dC.IQlGlRwRTWYIHHoh70jC6rg3Z23gsk8y6oGa9D3IeQgHKHS.KIdJ_G.rw1ZkPY2NPQo0N2noGMMppLAZk";

         RestTemplate restTemplate = new RestTemplate();

         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_JSON);
         headers.add("Authorization","Bearer "+accessToken);


         JsonObject jsonObject = new JsonObject();
         jsonObject.addProperty("Name",name);


         HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(),headers);


         ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);


         log.info("name={}",name);

         return  response.toString();



     }
}
