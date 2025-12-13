package com.kajiharuhyyy.customermanager.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kajiharuhyyy.customermanager.domain.Customer;
import com.kajiharuhyyy.customermanager.domain.CustomerStatus;
import com.kajiharuhyyy.customermanager.repo.CustomerRepository;
import com.kajiharuhyyy.customermanager.web.form.CustomerForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> search(String keyword, CustomerStatus status, Boolean active) {

        List<Customer> customers = customerRepository.findAll();
        
        if (keyword != null && !keyword.isBlank()) {
            customers = customerRepository.findByNameContainingIgnoreCase(keyword);
        }

        if (status != null) {
            customers = customers.stream()
                    .filter(c -> c.getStatus() == status)
                    .toList();
        }

        if (active != null) {
            customers = customers.stream()
                    .filter(c -> c.isActive() == active)
                    .toList();
        }

        return customers;
    }

    @Transactional
    public Customer create(CustomerForm form) {
        Customer customer = Customer.builder()
                .name(form.getName())
                .email(form.getEmail())
                .phone(form.getPhone())
                .rank(form.getRank())
                .status(form.getStatus())
                .memo(form.getMemo())
                .active(form.getActive())
                .build();

        return customerRepository.save(customer);
    }

    public Customer getById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID: " + id));
    }

    @Transactional
    public Customer update(long id, CustomerForm form) {
        Customer customer = getById(id);

        customer.setName(form.getName());
        customer.setEmail(form.getEmail());
        customer.setPhone(form.getPhone());
        customer.setRank(form.getRank());
        customer.setStatus(form.getStatus());
        customer.setMemo(form.getMemo());
        customer.setActive(form.getActive());

        return customer;
    }

    @Transactional
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
}
