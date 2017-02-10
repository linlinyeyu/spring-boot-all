package com.ybliu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class AngularBootstrapApplication {
	
	@RequestMapping(value="/search",produces={MediaType.APPLICATION_JSON_VALUE})
	public Person search(String personName){
		return new Person(personName,32,"zhejiang");
	}

	public static void main(String[] args) {
		SpringApplication.run(AngularBootstrapApplication.class, args);
	}
}
