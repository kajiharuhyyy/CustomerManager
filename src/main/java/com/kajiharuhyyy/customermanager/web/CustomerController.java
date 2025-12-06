package com.kajiharuhyyy.customermanager.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kajiharuhyyy.customermanager.domain.Customer;
import com.kajiharuhyyy.customermanager.domain.CustomerRank;
import com.kajiharuhyyy.customermanager.domain.CustomerStatus;
import com.kajiharuhyyy.customermanager.repo.CustomerRepository;
import com.kajiharuhyyy.customermanager.web.form.CustomerForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;

    @GetMapping("/customers")
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "customers/list";
    }

    @GetMapping("/customers/new")
    public String newCustomerForm(Model model) {
        CustomerForm form = new CustomerForm();
        form.setActive(true);
        model.addAttribute("customerForm", form);
        return "customers/new";
    }

    @PostMapping("/customers")
    public String createCustomers(@Valid @ModelAttribute CustomerForm form,
        BindingResult bindingResult) {

            if (bindingResult.hasErrors()) {
                return "customers/new";
            }

        Customer customer = Customer.builder()
            .name(form.getName())
            .email(form.getEmail())
            .phone(form.getPhone())
            .rank(form.getRank())
            .status(form.getStatus())
            .memo(form.getMemo())
            .active(form.getActive())
            .build();

        customerRepository.save(customer);
        return "redirect:/customers";	
        }


}
