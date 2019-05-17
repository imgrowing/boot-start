package my.bootstart.repository;

import my.bootstart.domain.JournalSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<JournalSummary, Long> {
}
