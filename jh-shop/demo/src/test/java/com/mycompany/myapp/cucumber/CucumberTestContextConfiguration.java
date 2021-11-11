package com.mycompany.myapp.cucumber;

import com.mycompany.myapp.DemoApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = DemoApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
