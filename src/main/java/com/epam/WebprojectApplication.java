package com.epam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@SpringBootApplication
public class WebprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebprojectApplication.class, args);
	}
	EntityManagerFactory emf;
	@Bean
    public EntityManager getEntityManager()
	{
		emf=Persistence.createEntityManagerFactory("quizdb");
		return emf.createEntityManager();
		
	}
	@PreDestroy
		void destroy()
		{
			emf.close();
		}
	}

