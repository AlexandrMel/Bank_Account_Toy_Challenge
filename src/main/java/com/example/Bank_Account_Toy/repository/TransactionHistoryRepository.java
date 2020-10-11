package com.example.Bank_Account_Toy.repository;

import com.example.Bank_Account_Toy.io.entity.TransactionHistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionHistoryRepository extends CrudRepository<TransactionHistoryEntity, Long> {


}
