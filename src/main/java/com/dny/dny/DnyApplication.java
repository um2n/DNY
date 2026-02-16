package com.dny.dny;

import com.dny.dny.service.JobService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@EnableAsync
public class DnyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DnyApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(JobService jobService) {
		return args -> {
			System.out.println("서버 시작");
			jobService.saveJobsToDb();
		};
	}
}
