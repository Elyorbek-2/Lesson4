package u.pdp.lesson3.payload;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class IncomeDto {

    private long fromCard;

    private long toCard;

    private double amount;

    private Timestamp timestamp;

}
