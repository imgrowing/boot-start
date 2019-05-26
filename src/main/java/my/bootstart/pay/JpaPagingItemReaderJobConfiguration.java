package my.bootstart.pay;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JpaPagingItemReaderJobConfiguration {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory entityManagerFactory;

	private int chunkSize = 2;

	@Bean
	public Job jpaPagingItemReaderJob() {
		return jobBuilderFactory.get("jpaPagingItemReaderJob")
			.start(jpaPagingItemReaderStep())
			.build();
	}

	@Bean
	public Step jpaPagingItemReaderStep() {
		return stepBuilderFactory.get("jpaPagingItemReaderStep")
			.<Pay, Pay>chunk(chunkSize)
			.reader(jpaPagingItemReader())
			.writer(jpaPagingItemWriter())
			.build();
	}

	@Bean
	public JpaPagingItemReader<Pay> jpaPagingItemReader() {
		JpaPagingItemReader<Pay> reader = new JpaPagingItemReader<>();
		reader.setName("jpaPagingItemReader");
		reader.setEntityManagerFactory(entityManagerFactory);
		reader.setPageSize(chunkSize);
		reader.setQueryString("SELECT p FROM Pay p WHERE amount >= 2000");
		return reader;
	}

	private ItemWriter<? super Pay> jpaPagingItemWriter() {
		return list -> list.forEach(pay -> log.info(pay.toString()));
	}
}
