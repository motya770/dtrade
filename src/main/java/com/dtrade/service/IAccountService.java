package com.dtrade.service;

import com.dtrade.model.account.Account;
import java.util.*;
/**
 * Created by kudelin on 8/24/16.
 */
public interface IAccountService {

    Account getCurrentAccount();

    Account enable(Long accountId);

    Account disable(Long accountId);

    List<Account> findAll();

    Account find(Long accountId);


}
