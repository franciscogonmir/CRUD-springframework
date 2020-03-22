package com.spring.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.spring.jpa.service.UploadServices;

@SpringBootApplication
public class Application implements CommandLineRunner{

	@Autowired
	private UploadServices uploadSvc;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadSvc.deleteAll();
		uploadSvc.init();
		
		//ejemplo de encriptacion  de contraseña 
		/*String password = "12345";
		
		for(int i=0;i<2;i++) {
			String pc = this.passwordEncoder.encode(password);
			System.out.println("Contraseña encryptada : "+pc);
		}*/
	}
}
