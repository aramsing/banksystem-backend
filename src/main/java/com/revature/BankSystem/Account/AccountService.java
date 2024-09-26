package com.revature.BankSystem.Account;

import com.revature.BankSystem.Exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** SERVICE CLASS DOCUMENTATION
 * @author Arjun Ramsinghani
 * The Service class is used to define the business logic coming to the backend by use of Spring Boot and will communicate with the database through Spring Data.
 * Instructions on how each method should interact are within each method call.
 */

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * This method creates an account.
     * @param account - account we want to create
     * @return the new account
     */
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    /**
     * This method will get an account by its id.
     * @param id - a unique id we want to find an account by
     * @return the whole account
     */
    public Account getAccountById(int id) {
        return accountRepository.findById(id).orElseThrow(() -> new DataNotFoundException("No Account found with account Id Number " + id));
    }

    /**
     * This method will get all accounts by their holder id.
     * @param holderId - a unique id we want to find all accounts a person owns
     * @return a list of accounts
     */
    public List<Account> getAllAccountsByProfileId(List<Integer> holderId) {
        return accountRepository.findAllById(holderId);
    }

    /**
     * This method will deposit money into an account.
     * @param amount - the amount we want to deposit
     * @param id - the id of the account we want to deposit to
     * @return the updated account
     */
    @Transactional
    public Account deposit(double amount, int id) {
        Account account = getAccountById(id);

        if (amount < 0) {
            throw new IllegalArgumentException("Deposit amount cannot be negative");
        }

        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    /**
     * This method will withdraw money from an account.
     * @param amount - the amount we want to withdraw
     * @param id - the id of the account we want to withdraw from
     * @return the updated account
     */
    @Transactional
    public Account withdraw(double amount, int id) {
        Account account = getAccountById(id);

        if (amount < 0) {
            throw new IllegalArgumentException("Withdrawal amount cannot be negative");
        }

        if (amount > account.getBalance()) {
            throw new IllegalArgumentException("Insufficient funds"); // checks if the amount to withdraw is greater than the balance
        }

        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }

    /**
     * This method will delete an account by its id.
     * @param accountId - the id of the account we want to delete
     * @return true if the account was deleted successfully, false otherwise
     */
    public boolean deleteAccountById(int accountId) {
        accountRepository.deleteById(accountId);
        return !accountRepository.existsById(accountId);
    }
}