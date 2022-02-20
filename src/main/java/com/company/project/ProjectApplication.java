package com.company.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.company.project.eventListener.AfterLoadEventListener;
import com.company.project.eventListener.BeforeSaveEventListener;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Bean
    public BeforeSaveEventListener beforeSaveEventListener() {
        return new BeforeSaveEventListener();
    }

    @Bean
    public AfterLoadEventListener afterLoadEventListener() {
        return new AfterLoadEventListener();
    }
}