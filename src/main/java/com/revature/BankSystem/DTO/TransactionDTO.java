package com.revature.BankSystem.DTO;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

/**
 * DATA TRANSFER OBJECT DOCUMENTATION
 * @author Arjun Ramsinghani
 * The DTO class define objects that carry data between processes in order to reduce the number of method calls.
 * The pattern's main purpose is to reduce roundtrips between the front and back end by batching them together in a single call.
 * A DTO is useful with remote calls as they help reduce the number of calls.
 */

@Data
public class TransactionDTO {
    @PositiveOrZero
    private double amount;
}