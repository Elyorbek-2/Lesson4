package u.pdp.lesson3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import u.pdp.lesson3.entity.Card;
import u.pdp.lesson3.entity.Income;
import u.pdp.lesson3.entity.Outcome;
import u.pdp.lesson3.payload.IncomeDto;
import u.pdp.lesson3.repository.CardRepository;
import u.pdp.lesson3.repository.OutcomeRepository;
import u.pdp.lesson3.result.Result;

import java.util.Optional;

@Service
public class OutcomeService {
    @Autowired
    OutcomeRepository outcomeRepository;
    @Autowired
    CardRepository cardRepository;

    public Page<Outcome> get() {
        Pageable pageable = PageRequest.of(0, 10);
        return outcomeRepository.findAll(pageable);
    }


    public Page<Outcome> getOne(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return outcomeRepository.findAll(pageable);
    }


    public Result addIncome(IncomeDto incomeDto) {
        Outcome outcome=new Outcome();
        outcome.setAmount(incomeDto.getAmount());
        outcome.setTimestamp(incomeDto.getTimestamp());
        Optional<Card> optionalFromCard = cardRepository.findByCardNumber(incomeDto.getFromCard());
        if (!optionalFromCard.isPresent())
            return new Result("Bunday raqamli from card mavjud emas", false);
        outcome.setFromCard(optionalFromCard.get());
        Optional<Card> optionalToCard = cardRepository.findByCardNumber(incomeDto.getToCard());
        if (!optionalToCard.isPresent())
            return new Result("Bunday raqamli to card mavjud emas", false);
        outcome.setToCard(optionalToCard.get());
        Card fromCard = optionalFromCard.get();
        double fromCardBalance = fromCard.getBalance();
        Card toCard = optionalToCard.get();
        double toCardBalance = toCard.getBalance();
        double sum = incomeDto.getAmount();
        if (toCardBalance>sum + (sum / 100 * 2)){
            toCard.setBalance(toCard.getBalance() - (sum + (sum / 100 * 2)));
            fromCard.setBalance(fromCardBalance + incomeDto.getAmount());
            cardRepository.save(fromCard);
            cardRepository.save(toCard);
            outcomeRepository.save(outcome);
            return new Result("Bajarildi hisobingizda " + fromCardBalance
                    + " miqdorda mablag mavjud", true);
        }
        else
            return new Result("Mablag yetarli emas",false);
    }


    public Result edit(Integer id,IncomeDto incomeDto){
        Optional<Outcome> optionalOutcome = outcomeRepository.findById(id);
        if (!optionalOutcome.isPresent())
            return new Result("Bunday id li income mavjud emas",false);
        Outcome outcome = optionalOutcome.get();
        outcome.setAmount(incomeDto.getAmount());
        outcome.setTimestamp(incomeDto.getTimestamp());
        Optional<Card> optionalFromCard = cardRepository.findByCardNumber(incomeDto.getFromCard());
        if (!optionalFromCard.isPresent())
            return new Result("Bunday raqamli from card mavjud emas", false);
        outcome.setFromCard(optionalFromCard.get());
        Optional<Card> optionalToCard = cardRepository.findByCardNumber(incomeDto.getToCard());
        if (!optionalToCard.isPresent())
            return new Result("Bunday raqamli to card mavjud emas", false);
        outcome.setToCard(optionalToCard.get());
        Card fromCard = optionalFromCard.get();
        double fromCardBalance = fromCard.getBalance();
        Card toCard = optionalToCard.get();
        double toCardBalance = toCard.getBalance();
        double sum = incomeDto.getAmount();
        if (toCardBalance>sum + (sum / 100 * 2)){
            toCard.setBalance(toCard.getBalance() - (sum + (sum / 100 * 2)));
            fromCard.setBalance(fromCardBalance + incomeDto.getAmount());
            cardRepository.save(fromCard);
            cardRepository.save(toCard);
            outcomeRepository.save(outcome);
            return new Result("Bajarildi hisobingizda " + fromCardBalance
                    + " miqdorda mablag mavjud", true);
        }
        else
            return new Result("Mablag yetarli emas",false);
    }

    public Result delete(Integer id){
        try {
            outcomeRepository.deleteById(id);
            return new Result("Bajarildi",true);
        } catch (Exception e) {
            return new Result("Ochirish amalga oshirilmadi",false);
        }
    }
}
