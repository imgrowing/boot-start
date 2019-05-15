package my.bootstart.service;

import lombok.extern.slf4j.Slf4j;
import my.bootstart.domain.Journal;
import my.bootstart.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class JournalService {
	@Autowired
	private JournalRepository repository;

	public void loadData() {
		log.info("> 데이터 생성");
		repository.save(new Journal("제목1", "요약1", "2019-01-01 11:11:11"));
		repository.save(new Journal("제목2", "요약2", "2019-01-02 12:12:12"));
		repository.save(new Journal("제목3", "요약3", "2019-01-03 13:13:13"));
		repository.save(new Journal("제목4", "요약4", "2019-01-04 14:14:14"));
		log.info("> 저장 완료");
	}

	public List<Journal> findAll() {
		return repository.findAll();
	}
}
