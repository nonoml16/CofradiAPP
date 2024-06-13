package com.nonomartinez.sfc.cofradiasapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(description = "API de Cofradias de la Semana Santa de Sevilla",
		version = "1.0",
		contact = @Contact(email = "nonoml.dev@gmail.com", name = "Antonio Martínez"),
		license = @License(name = "Creative Commons"),
		title = "Cofradias API"
)
)
public class CofradiasApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CofradiasApiApplication.class, args);
	}

}
