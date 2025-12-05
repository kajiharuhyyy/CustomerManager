package com.kajiharuhyyy.customermanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kajiharuhyyy.customermanager.domain.Customer;
import com.kajiharuhyyy.customermanager.domain.CustomerStatus;

import java.util.List;
import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Long>{

    List<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    List<Customer> findByPhone(String phone);

    List<Customer> findByRank(String rank);

    List<Customer> findByStatus(CustomerStatus status);

    List<Customer> findByMemo(String memo);

    List<Customer> findByActive(boolean active);

}
