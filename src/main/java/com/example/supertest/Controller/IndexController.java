package com.example.supertest.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class IndexController
{
    @GetMapping()
    public String index() { return "index.html";}

    @GetMapping("drgemTest")
    public String drgemTest(){return "drgemTest.html";}
}
