package br.com.viavarejo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TesteViaVarejoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteViaVarejoApplication.class, args);
	}

}
