package com.revature.BankSystem.Account;

import com.revature.BankSystem.Profile.Profile;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// LOMBOK ANNOTATIONS
@Data // includes ToString, EqualsAndHashCode, Getter, and Setter Annotations
@NoArgsConstructor // builds a no-args constructor to reduce boiler-plate code
@AllArgsConstructor // builds an all args constructor to reduce boiler-plate code

// JAKARTA ANNOTATIONS
@Entity // shows that the class represents a data object that should be persisted, requires an id for a primary key and a no-arg constructor
@Table(name = "account") // defines what table a class should belong to
public class Account {
    @Id // makes a public key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // allows the primary key to be generated by the database itself
    private int id;

    @Column(nullable = false)
    private int holderId;

    @Column(nullable = false)
    @PositiveOrZero
    private double balance; // should be double for calculations

    @Column(nullable = false)
    private String accountType; // works without enum

    public Account(int holderId, double balance, String accountType) {
        this.holderId = holderId;
        this.balance = balance;
        this.accountType = accountType;
    }
}