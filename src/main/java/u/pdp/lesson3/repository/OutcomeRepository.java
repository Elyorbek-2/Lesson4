package u.pdp.lesson3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import u.pdp.lesson3.entity.Outcome;

public interface OutcomeRepository extends JpaRepository<Outcome,Integer> {
}
