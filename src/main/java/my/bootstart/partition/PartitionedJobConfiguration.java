package my.bootstart.partition;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PartitionedJobConfiguration {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	private static final int GRID_SIZE = 10;
	private static final int CHUNK_SIZE = 2;

	@Bean
	public TaskExecutor asyncTaskExecutor() {
		return new SimpleAsyncTaskExecutor("partition-");
	}

	@Bean
	public Job partitionedJob() {
		return jobBuilderFactory.get("partitionedJob")
			.start(masterStep())
			.build();
	}

	@Bean
	public Step masterStep() {
		return stepBuilderFactory.get("masterStep")
			.partitioner("slaveStep", rangePartitioner())
			.gridSize(GRID_SIZE)
			.taskExecutor(asyncTaskExecutor())
			.step(slave())
			.build();
	}

	@Bean
	public Step slave() {
		return stepBuilderFactory.get("slave")
			.<User, User>chunk(CHUNK_SIZE)
			.reader(userReader(-1, -1))
			.processor(userProcessor())
			.writer(userWriter())
			.build();
	}

	@Bean
	@StepScope
	public ItemReader<User> userReader(
		@Value("#{stepExecutionContext[fromId]}") int fromId,
		@Value("#{stepExecutionContext[toId]}") int toId
	) {
		return new ItemReader<User>() {
			private int id = fromId;
			private int maxId = toId;

			@Override
			public User read() {
				if (id <= maxId) {
					User user = new User(id, "loginId" + id, "name" + id);
					id++;
					return user;
				}
				return null;
			}
		};
	}

	@Bean
	@StepScope
	public ItemProcessor<User,User> userProcessor() {
		return user -> {
			log.info("processing : " + user.toString());
			return user;
		};
	}

	@Bean
	@StepScope
	public ItemWriter<User> userWriter() {
		return users -> {
			String usersStr = users.stream().map(User::toString).collect(Collectors.joining(", "));
			log.warn("userWriter: " + usersStr);
		};
	}

	@Bean
	public Partitioner rangePartitioner() {
		return new RangePartitioner();
	}
}
