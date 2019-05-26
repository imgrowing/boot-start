package my.bootstart.partition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RangePartitioner implements Partitioner {
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		Map<String, ExecutionContext> partitions = new HashMap<>();

		int range = 10;
		int fromId = 1;
		int toId = range;

		for (int i = 1; i <= gridSize; i++) {
			ExecutionContext executionContext = new ExecutionContext();

			log.info("Starting: partition " + i + " - fromId: " + fromId + ", toId: " + toId);

			executionContext.putInt("fromId", fromId);
			executionContext.putInt("toId", toId);

			// give each thread a name, thread 1, 2, 3
			executionContext.putString("name", "Thread" + i);

			partitions.put("partition" + i, executionContext);

			fromId = toId + 1;
			toId += range;
		}

		return partitions;
	}
}
