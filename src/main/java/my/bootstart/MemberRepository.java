package my.bootstart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@Slf4j
public class MemberRepository {

	public Member findByNameNoCache(String name) {
		slowQuery(2000);
		return new Member(0, name + "@gmail.com", name);
	}

	@Cacheable(value = "findMemberCache", key = "#name")
	public Member findByNameCache(String name) {
		slowQuery(2000);
		return new Member(0, name + "@gmail.com", name);
	}

	@CacheEvict(value = "findMemberCache", key = "#name")
	public void refresh(String name) {
		log.warn("{}의 Cache Clear!", name);
	}

	private void slowQuery(long millis) {
		try {
			TimeUnit.MILLISECONDS.sleep(millis);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
}
