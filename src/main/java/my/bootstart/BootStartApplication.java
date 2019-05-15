package my.bootstart;

import lombok.extern.slf4j.Slf4j;
import my.bootstart.service.JournalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class BootStartApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootStartApplication.class, args);
	}

	@Bean
	CommandLineRunner start(JournalService journalService) {
		return (args) -> {
			log.warn("## 데이터 생성 ...");
			journalService.loadData();
			log.warn("## findAll() 호출");
			journalService.findAll().forEach(e -> log.info(e.toString()));
		};
	}
}
