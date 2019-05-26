package my.bootstart.pay;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JdbcBatchItemWriterJobConfiguration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final DataSource dataSource;
	private static final int chunkSize = 2;

	@Bean
	public Job jdbcBatchItemWriterJob() {
		return jobBuilderFactory.get("jdbcBatchItemWriterJob")
			.start(jdbcBatchItemWriterStep())
			.build();
	}

	@Bean
	public Step jdbcBatchItemWriterStep() {
		return stepBuilderFactory.get("jdbcBatchItemWriterStep")
			.<Pay, Pay>chunk(chunkSize)
			.reader(jdbcCursorItemReader())
			.writer(jdbcBatchItemWriter())
			.build();
	}

	@Bean
	public JdbcCursorItemReader<Pay> jdbcCursorItemReader() {
		JdbcCursorItemReader<Pay> reader = new JdbcCursorItemReader<>();
		reader.setName("jdbcCursorItemReader");
		reader.setDataSource(dataSource);
		reader.setFetchSize(chunkSize);
		reader.setSql("SELECT id, amount, tx_name, tx_date_time FROM pay");
		reader.setRowMapper(new BeanPropertyRowMapper<>(Pay.class));
		return reader;
	}

	@Bean
	public JdbcBatchItemWriter<Pay> jdbcBatchItemWriter() {
		JdbcBatchItemWriter<Pay> writer = new JdbcBatchItemWriter<>();
		writer.setDataSource(dataSource);
		writer.setSql("INSERT INTO pay2 (amount, tx_name, tx_date_time) values (:amount, :txName, :txDateTime)");
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		return writer;
	}
}
