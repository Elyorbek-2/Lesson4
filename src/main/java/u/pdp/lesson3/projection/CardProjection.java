package u.pdp.lesson3.projection;

import org.springframework.data.rest.core.config.Projection;
import u.pdp.lesson3.entity.Card;

import java.sql.Timestamp;

@Projection(types = Card.class)
public interface CardProjection {
    String getUserName();
    long getCardNumber();
    double getBalance();
    Timestamp getTimestamp();
    boolean isActive();
}
