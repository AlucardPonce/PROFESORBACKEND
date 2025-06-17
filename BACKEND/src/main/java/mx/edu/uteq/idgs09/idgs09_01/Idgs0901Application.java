package mx.edu.uteq.idgs09.idgs09_01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class Idgs0901Application {

	public static void main(String[] args) {
		SpringApplication.run(Idgs0901Application.class, args);
	}

}