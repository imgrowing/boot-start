package my.bootstart.job;

import my.bootstart.BatchTestConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
// SimpleJobConfiguration.class 만 선택하여 지정하지 않으면, @Configuration 내의 모든 Job이 job bean으로 등록되며
// autowired 시에 Qualifier를 지정해도 해당 bean과 연결되지 않음
@ContextConfiguration(classes = { SimpleJobConfiguration.class, BatchTestConfiguration.class })
//@TestPropertySource(properties = "job.name=simpleJob1") -> 동작에 영향을 주지 않음
@RunWith(SpringRunner.class)
public class SimpleJobConfigurationTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	// @Qualifier("simpleJob") // ContextConfiguration에 SimpleJobConfiguration.class 만 지정하였으므로, @Qualifier 가 의미 없는 상황임 (job bean은 한 개만 존재하므로)
	private Job job;

	@Test
	public void jobParameter_생성시점() throws Exception {
		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addString("requestDate", "20190505");

		// when
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(builder.toJobParameters());

		// then
		Assert.assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));
	}
}