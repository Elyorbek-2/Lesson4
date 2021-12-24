package u.pdp.lesson3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import u.pdp.lesson3.entity.Income;
import u.pdp.lesson3.payload.IncomeDto;
import u.pdp.lesson3.result.Result;
import u.pdp.lesson3.service.IncomeService;

@RestController
@RequestMapping("/api/income")
public class IncomeController {
    @Autowired
    IncomeService incomeService;

    @GetMapping
    public ResponseEntity<?> getOne(@PathVariable Integer number){
        return ResponseEntity.ok(incomeService.getOne(number));
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody IncomeDto incomeDto){
        Result result = incomeService.addIncome(incomeDto);
        return ResponseEntity.status(result.isActive()?200:400).body(result);
    }

    @PutMapping
    public ResponseEntity<?> edit(@PathVariable Integer id,@RequestBody IncomeDto incomeDto){
        Result edit = incomeService.edit(id, incomeDto);
        return ResponseEntity.status(edit.isActive()?200:400).body(edit);
    }
    @DeleteMapping
    public ResponseEntity<?> delete(@PathVariable Integer id){
        Result delete = incomeService.delete(id);
        return ResponseEntity.status(delete.isActive()?200:400).body(delete);
    }


}
