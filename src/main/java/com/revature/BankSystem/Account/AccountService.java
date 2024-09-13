package com.revature.BankSystem.Account;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional<Account> getAccountById(int id) {
        return accountRepository.findById(id);
    }

    @Transactional
    public Account deposit(int moneyToDeposit, Account account) {
        double balance = account.getBalance();
        balance += moneyToDeposit;
        account.setBalance(balance);
        return accountRepository.saveAndFlush(account);
    }

    @Transactional
    public Account withdraw(int moneyToWithdraw, Account account) {
        double balance = account.getBalance();
        balance -= moneyToWithdraw;
        account.setBalance(balance);
        return accountRepository.saveAndFlush(account);
    }

    public boolean deleteAccount(int id) {
        accountRepository.deleteById(id);
        return true;
    }
}
