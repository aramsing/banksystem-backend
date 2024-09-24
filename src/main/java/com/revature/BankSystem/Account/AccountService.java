package com.revature.BankSystem.Account;

import com.revature.BankSystem.Exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccountById(int id) {
        return accountRepository.findById(id).orElseThrow(() -> new DataNotFoundException("No Account found with account Id Number " + id));
    }

    public List<Account> getAllAccountsByProfileId(List<Integer> holderId) {
        return accountRepository.findAllById(holderId);
    }

    @Transactional
    public Account deposit(double amountToDeposit, Account account) {
        if (amountToDeposit < 0) {
            throw new IllegalArgumentException("Deposit amount cannot be negative");
        }

        account.setBalance(account.getBalance() + amountToDeposit);
        return accountRepository.save(account);
    }

    @Transactional
    public Account withdraw(double amount, Account account) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot withdraw negative amount");
        }

        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }

    public boolean deleteAccountById(int accountId) {
        accountRepository.deleteById(accountId);
        return !accountRepository.existsById(accountId);
    }
}