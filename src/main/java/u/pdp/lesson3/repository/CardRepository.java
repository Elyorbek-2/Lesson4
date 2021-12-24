package u.pdp.lesson3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import u.pdp.lesson3.entity.Card;
import u.pdp.lesson3.projection.CardProjection;

import java.util.Optional;

@RepositoryRestResource(path = "card",excerptProjection = CardProjection.class)
public interface CardRepository extends JpaRepository<Card,Integer> {
    boolean existsByCardNumber(long cardNumber);
    Optional<Card> findByCardNumber(long cardNumber);
}
