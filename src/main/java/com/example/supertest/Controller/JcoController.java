package com.example.supertest.Controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.supertest.Client.JcoClient;
import com.example.supertest.Wrapper.Company_Info__c;

@RestController
public class JcoController {
    @RequestMapping(value = "/getCompany", method = RequestMethod.GET, produces =  MediaType.APPLICATION_JSON_VALUE)
    public Company_Info__c getCompany(@RequestParam String code, HttpServletResponse response) {
        try {
            Company_Info__c company = JcoClient.getCompany(code);
            return company;
        } catch (Exception e) {
            System.out.println(e.toString());
            Company_Info__c company = new Company_Info__c(e.toString());
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return company;
        }
    }
}
