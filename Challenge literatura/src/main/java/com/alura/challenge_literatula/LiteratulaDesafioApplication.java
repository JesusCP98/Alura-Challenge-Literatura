package com.alura.challenge_literatula;


import com.alura.challenge_literatula.principal.Main;
import com.alura.challenge_literatula.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteratulaDesafioApplication implements CommandLineRunner {

	//Inyeccion de dependencias
	@Autowired
	//Dependencia
			AuthorRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(LiteratulaDesafioApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repo);
		main.menu();
		System.exit(0);


	}
}
