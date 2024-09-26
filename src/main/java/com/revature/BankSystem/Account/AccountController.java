package com.revature.BankSystem.Account;

import com.revature.BankSystem.DTO.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** CONTROLLER CLASS DOCUMENTATION
 * @author Arjun Ramsinghani
 * The Controller class will interact with the front end part of our application and send the data coming through there through our Service class for processing and validation.
 * Instructions on how each method should interact are within each method call.
 */

@CrossOrigin
@RestController
@RequestMapping("/accounts")
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     *
     * @param account
     * @return
     */
    @PostMapping
    public ResponseEntity<Account> postCreateAccount(@RequestBody Account account) {
        Account newAccount = accountService.createAccount(account);

        if (newAccount == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable int id) {
        Account account = accountService.getAccountById(id);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    /**
     *
     * @param id
     * @param amount
     * @return
     */
    @PutMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable int id, @RequestBody TransactionDTO amount) {
        Account updatedAccount = accountService.deposit(amount.getAmount(), id);

        if (updatedAccount == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(updatedAccount);
    }

    /**
     *
     * @param id
     * @param amount
     * @return
     */
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable int id, @RequestBody TransactionDTO amount) {
        Account updatedAccount = accountService.withdraw(amount.getAmount(), id);

        if (updatedAccount == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(updatedAccount);
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAccountById(@PathVariable int id) {
        boolean isDeleted = accountService.deleteAccountById(id);

        if (isDeleted == false) {
            ResponseEntity.status(HttpStatus.OK).body(false);
        }

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}