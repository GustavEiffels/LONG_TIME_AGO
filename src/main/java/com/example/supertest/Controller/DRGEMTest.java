package com.example.supertest.Controller;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@RestController
@Slf4j
@RequestMapping("drgem")
public class DRGEMTest
{



    /** HTML 에서 Account name 을 설정하면 Account 생성하기 */
     @PostMapping("createAccount")
    public String createAccount(String name)
     {
         /** Account 를 생성하기 위한 URL*/
         String url = "https://drgem2--sbtdev.sandbox.my.salesforce.com/services/data/v53.0/sobjects/Account";

         /** Access Token */
         String accessToken = "00D9D0000000aTP!ARYAQHmZYTO97x4yJDusfcuh.09geOjKkF38k31KUbVEO._.DumBmnaNqZ8hJ78.wAGrqASgwI41Jk0bgMYqNeD.2e3VKZzV";


         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_JSON);
         headers.add("Authorization","Bearer "+accessToken);


         JsonObject jsonObject = new JsonObject();
         jsonObject.addProperty("Name",name);

        RestTemplate restTemplate = new RestTemplate();
         HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(),headers);


         ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);


         log.info("name={}",name);

         return  response.toString();

     }

     /** Access Token 이 만료 될 경우 제발급 받기 */
     @PostMapping("generate_AccessToken")
     public String generate_AccessToken_By_RefreshToken() throws ParseException {
         /** REQUEST URI*/
         String url = "https://test.salesforce.com/services/oauth2/token";

         /** Client_id*/
         String client_id = "3MVG9N6eDmZRVJOntO53kiZQBLGpTPIFhvU2Yh27tAEArDctaokiORn..jwvfcf2A9__05peMmn0L0SMtWSLy";

         /** Client_secret*/
         String client_secret = "DF0A30A676947D7FEE1628419395201C0DAA00F6E4968E4C60C1B0746639F35C";

         /** Refresh token */
         String refresh_token = "5Aep861fB57v8EKTBDOKpSL.MCj.Euj8aqQRsef4Ma.av3sYrO4gxz_eB_vGprRSY3fzMzBWYA4zoQcrCzQ8GFW";


         String body = "grant_type=refresh_token"+"&client_id="+client_id+"&client_secret="+client_secret+"&refresh_token="+refresh_token;

         HttpHeaders headers = new HttpHeaders();
         headers.add("Content-Type","application/x-www-form-urlencoded");


         HttpEntity<String> entity = new HttpEntity<String>(body,headers);

         RestTemplate restTemplate = new RestTemplate();
         ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);



//         String result = response.toString();
//
//
//         JSONParser jsonParser = new JSONParser();
//         JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
//
//
//         String accessToken = (String)jsonObject.get("access_token");
//
//
//         log.info("accessToken = {}",accessToken);
//
//
//         return accessToken;

         return response.toString();
     }

}
