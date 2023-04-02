package com.finalproject.StayFinderApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.finalproject.StayFinderApi.property.FileStorageProperties;

@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class StayFinderApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StayFinderApiApplication.class, args);
	}

}
