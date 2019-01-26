package com.spring.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.jpa.service.UploadServices;

@SpringBootApplication
public class Application implements CommandLineRunner{

	@Autowired
	private UploadServices uploadSvc;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadSvc.deleteAll();
		uploadSvc.init();
	}
}
