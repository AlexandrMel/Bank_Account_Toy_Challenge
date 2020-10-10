package com.example.Bank_Account_Toy.repository;

import com.example.Bank_Account_Toy.io.entity.AccountEntity;
import com.example.Bank_Account_Toy.shared.dto.AccountDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {
    List<AccountEntity> findAccountEntityByAccountType(String accountType);

    AccountEntity findAccountByIban(String iban);

}
