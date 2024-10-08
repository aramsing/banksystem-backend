package com.revature.BankSystem.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** REPOSITORY CLASS DOCUMENTATION
 * @author Arjun Ramsinghani
 * The Repository will use Jpa Repository to use its methods for database connections therefore no code will be written here unless we need something that is not there.
 */

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}