package my.bootstart.pay;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JdbcPagingItemReaderJobConfiguration {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	private final DataSource dataSource;

	private static final int chunkSize = 2;

	@Bean
	public Job jdbcPagingItemReaderJob() throws Exception {
		return jobBuilderFactory.get("jdbcPagingItemReaderJob")
			.start(jdbcPagingItemReaderStep())
			.build();
	}

	@Bean
	public Step jdbcPagingItemReaderStep() throws Exception {
		return stepBuilderFactory.get("jdbcCursorItemReaderStep")
			.<Pay, Pay>chunk(chunkSize)
			.reader(jdbcPagingItemReader())
			.writer(jdbcPagingItemWriter())
			.build();
	}

	@Bean
	public ItemReader<Pay> jdbcPagingItemReader() throws Exception {
		Map<String, Object> parameterValues = new HashMap<>();
		parameterValues.put("amount", 2000);

		JdbcPagingItemReader<Pay> reader = new JdbcPagingItemReader<>();
		reader.setFetchSize(chunkSize);
		reader.setDataSource(dataSource);
		reader.setRowMapper(new BeanPropertyRowMapper<>(Pay.class));
		reader.setQueryProvider(createQueryProvider());
		reader.setParameterValues(parameterValues);
		reader.setPageSize(chunkSize);
		reader.setName("jdbcPagingItemReader");
		return reader;
	}

	@Bean
	public PagingQueryProvider createQueryProvider() throws Exception {
		SqlPagingQueryProviderFactoryBean provider = new SqlPagingQueryProviderFactoryBean();
		provider.setDataSource(dataSource);
		provider.setSelectClause("id, amount, tx_name, tx_date_time");
		provider.setFromClause("FROM pay");
		provider.setWhereClause("WHERE amount >= :amount");

		Map<String, Order> sortKeys = new HashMap<>();
		sortKeys.put("id", Order.ASCENDING);

		provider.setSortKeys(sortKeys);
		return provider.getObject();
	}

	@Bean
	public ItemWriter<Pay> jdbcPagingItemWriter() {
		return list -> list.forEach(pay -> log.info(pay.toString()));
	}

}
