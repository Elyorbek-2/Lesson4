package u.pdp.lesson3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import u.pdp.lesson3.entity.Card;
import u.pdp.lesson3.entity.Income;

public interface IncomeRepository extends JpaRepository<Income,Integer> {
//    boolean existsByFromCard_CardNumber(long fromCard_cardNumber);
//    boolean existsByToCard_CardNumber(long toCard_cardNumber);
//    boolean existsIncomeByFromCardAndId(Card fromCard, Integer id);

//    double findByFromCard_CardNumber(long fromCard_cardNumber);

}
