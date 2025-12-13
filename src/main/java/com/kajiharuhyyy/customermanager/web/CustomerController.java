package com.kajiharuhyyy.customermanager.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String listCustomers(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) CustomerStatus status,
        @RequestParam(required = false) Boolean active,
        Model model) {
        
        // 全件取得
        List<Customer> customers = customerRepository.findAll(); 

        // 名前　（部分一致）
        if (keyword != null && !keyword.isBlank()) {
            customers = customerRepository.findByNameContainingIgnoreCase(keyword);
        }

        // 状態
        if (status != null) {
            customers = customers.stream()
                .filter(c -> c.getStatus() == status)
                .toList();
        }

        // 有効/無効
        if (active != null) {
            customers = customers.stream()
            .filter(c -> c.isActive() == active)
            .toList(); 
        }

        model.addAttribute("customers", customers);
        model.addAttribute("statuses", CustomerStatus.values());
        model.addAttribute("keyword", keyword);
        model.addAttribute( "selectedStatus", status);
        model.addAttribute("selectedActive", active);
        return "customers/list";
    }

    @GetMapping("/customers/new")
    public String newCustomerForm(Model model) {
        CustomerForm form = new CustomerForm();
        form.setActive(true);
        model.addAttribute("customerForm", form);
        model.addAttribute("ranks", CustomerRank.values());
        model.addAttribute("statuses", CustomerStatus.values());
        return "customers/new";
    }

    @PostMapping("/customers")
    public String createCustomers(
            @Valid @ModelAttribute CustomerForm form,
            BindingResult bindingResult,
            Model model) {

            if (bindingResult.hasErrors()) {
                model.addAttribute("ranks", CustomerRank.values());
                model.addAttribute("statuses", CustomerStatus.values());
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

    @GetMapping("/customers/{id}/edit")
    public String editCustomerForm(@PathVariable Long id,
            Model model) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

        CustomerForm form = new CustomerForm();
        form.setId(customer.getId());
        form.setName(customer.getName());
        form.setEmail(customer.getEmail());
        form.setPhone(customer.getPhone());
        form.setRank(customer.getRank());
        form.setStatus(customer.getStatus());
        form.setMemo(customer.getMemo());
        form.setActive(customer.isActive());

        model.addAttribute("customerForm", form);
        model.addAttribute("ranks", CustomerRank.values());
        model.addAttribute("statuses", CustomerStatus.values());
        return "customers/edit";
    }

    @PostMapping("/customers/{id}/edit")
    public String updateCustomer(@PathVariable Long id, 
            @Valid @ModelAttribute CustomerForm form, 
            BindingResult bindingResult, 
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ranks", CustomerRank.values());
            model.addAttribute("statuses", CustomerStatus.values());
            return "customers/edit";
        }

        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID" + id));

        customer.setName(form.getName());
        customer.setEmail(form.getEmail());
        customer.setPhone(form.getPhone());
        customer.setRank(form.getRank());
        customer.setStatus(form.getStatus());
        customer.setMemo(form.getMemo());
        customer.setActive(form.getActive());

        customerRepository.save(customer);
        return "redirect:/customers"; 
    }

    @PostMapping("/customers/{id}/delete")
    public String deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
        return "redirect:/customers"; 
    }

}
