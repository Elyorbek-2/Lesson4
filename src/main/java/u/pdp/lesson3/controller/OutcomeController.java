package u.pdp.lesson3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import u.pdp.lesson3.payload.IncomeDto;
import u.pdp.lesson3.result.Result;
import u.pdp.lesson3.service.OutcomeService;

@RestController
@RequestMapping("api/outcome")
public class OutcomeController {
    @Autowired
    OutcomeService outcomeService;
    @GetMapping
    public ResponseEntity<?> getOne(@PathVariable Integer number){
        return ResponseEntity.ok(outcomeService.getOne(number));
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody IncomeDto incomeDto){
        Result result = outcomeService.addIncome(incomeDto);
        return ResponseEntity.status(result.isActive()?200:400).body(result);
    }

    @PutMapping
    public ResponseEntity<?> edit(@PathVariable Integer id,@RequestBody IncomeDto incomeDto){
        Result edit = outcomeService.edit(id, incomeDto);
        return ResponseEntity.status(edit.isActive()?200:400).body(edit);
    }
    @DeleteMapping
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Result delete = outcomeService.delete(id);
        return ResponseEntity.status(delete.isActive() ? 200 : 400).body(delete);
    }
}
