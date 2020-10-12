package com.example.Bank_Account_Toy.repository;

import com.example.Bank_Account_Toy.io.entity.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Instantiating Account Repository extending the default CRUD Repository
@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {
    //Adding specific methods for getting data from DB
    List<AccountEntity> findAccountEntityByAccountType(String accountType);

    AccountEntity findAccountByIban(String iban);

}
