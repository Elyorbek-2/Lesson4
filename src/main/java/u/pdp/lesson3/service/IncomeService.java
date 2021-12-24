package u.pdp.lesson3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import u.pdp.lesson3.entity.Card;
import u.pdp.lesson3.entity.Income;
import u.pdp.lesson3.payload.IncomeDto;
import u.pdp.lesson3.repository.CardRepository;
import u.pdp.lesson3.repository.IncomeRepository;
import u.pdp.lesson3.result.Result;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {
    @Autowired
    IncomeRepository incomeRepository;
    @Autowired
    CardRepository cardRepository;

    public Page<Income> get() {
        Pageable pageable = PageRequest.of(0, 10);
        return incomeRepository.findAll(pageable);
    }


    public Page<Income> getOne(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return incomeRepository.findAll(pageable);
    }


    public Result addIncome(IncomeDto incomeDto) {
        Income income=new Income();
        income.setAmount(incomeDto.getAmount());
        income.setTimestamp(incomeDto.getTimestamp());
        Optional<Card> optionalFromCard = cardRepository.findByCardNumber(incomeDto.getFromCard());
        if (!optionalFromCard.isPresent())
            return new Result("Bunday raqamli from card mavjud emas", false);
        income.setFromCard(optionalFromCard.get());
        Optional<Card> optionalToCard = cardRepository.findByCardNumber(incomeDto.getToCard());
        if (!optionalToCard.isPresent())
            return new Result("Bunday raqamli to card mavjud emas", false);
        income.setToCard(optionalToCard.get());
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
            incomeRepository.save(income);
            return new Result("Bajarildi hisobingizda " + fromCardBalance
                    + " miqdorda mablag mavjud", true);
        }
        else
            return new Result("Mablag yetarli emas",false);
    }


    public Result edit(Integer id,IncomeDto incomeDto){
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if (!optionalIncome.isPresent())
            return new Result("Bunday id li income mavjud emas",false);
        Income income = optionalIncome.get();
        income.setAmount(incomeDto.getAmount());
        income.setTimestamp(incomeDto.getTimestamp());
        Optional<Card> optionalFromCard = cardRepository.findByCardNumber(incomeDto.getFromCard());
        if (!optionalFromCard.isPresent())
            return new Result("Bunday raqamli from card mavjud emas", false);
        income.setFromCard(optionalFromCard.get());
        Optional<Card> optionalToCard = cardRepository.findByCardNumber(incomeDto.getToCard());
        if (!optionalToCard.isPresent())
            return new Result("Bunday raqamli to card mavjud emas", false);
        income.setToCard(optionalToCard.get());
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
            incomeRepository.save(income);
            return new Result("Bajarildi hisobingizda " + fromCardBalance
                    + " miqdorda mablag mavjud", true);
        }
        else
            return new Result("Mablag yetarli emas",false);
    }

    public Result delete(Integer id){
        try {
            incomeRepository.deleteById(id);
            return new Result("Bajarildi",true);
        } catch (Exception e) {
            return new Result("Ochirish amalga oshirilmadi",false);
        }
    }


}
