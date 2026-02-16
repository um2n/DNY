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
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO) // Page 객체 JSON 직렬화 설정
@EnableAsync // 비동기 처리 활성화
public class DnyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DnyApplication.class, args);
	}

	/**
	 * 서버 시작 시 백그라운드에서 공고 데이터 수집 실행
	 */
	@Bean
	public CommandLineRunner run(JobService jobService) {
		return args -> {
			System.out.println("서버 시작 -> 공고 저장 실행");
			jobService.saveJobsToDb();
		};
	}
}
