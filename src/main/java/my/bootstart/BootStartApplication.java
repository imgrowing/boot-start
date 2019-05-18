package my.bootstart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableCaching
@RestController
@Slf4j
public class BootStartApplication {

	@Autowired
	private MemberRepository memberRepository;

	@GetMapping("/member/nocache/{name}")
	public Member getNoCacheMember(@PathVariable String name) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Member member = memberRepository.findByNameNoCache(name);
		stopWatch.stop();
		log.info("{}의 NoCache 수행시간: {}", name, stopWatch.getTotalTimeMillis());
		return member;
	}

	@GetMapping("/member/cache/{name}")
	public Member getCacheMember(@PathVariable String name) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Member member = memberRepository.findByNameCache(name);
		stopWatch.stop();
		log.info("{}의 Cache 수행시간: {}", name, stopWatch.getTotalTimeMillis());
		return member;
	}

	@GetMapping("/member/refresh/{name}")
	public String refresh(@PathVariable String name) {
		memberRepository.refresh(name);
		return "cache clear!";
	}

	@GetMapping("/")
	public String index() {
		return "HelloWorld";
	}

	public static void main(String[] args) {
		SpringApplication.run(BootStartApplication.class, args);
	}

	@Bean
	CommandLineRunner start() {
		return (args) -> {
			log.warn("Hello!!!");
		};
	}
}
