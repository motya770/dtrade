package com.dtrade.repository.account;

import com.dtrade.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kudelin on 8/24/16.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByMail(String mail);

    Account findAccountByGuidAndConfirmed(String guid, boolean confirmed);

}
