package com.cms_aop.service.impl;

import com.cms_aop.model.Customer;
import com.cms_aop.repository.CustomerRepository;
import com.cms_aop.service.GeneralService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService implements GeneralService<Customer> {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid argument!"));
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
        customerRepository.flush();
    }

    @Override
    public void remove(Long id) {
        customerRepository.deleteById(id);
    }
}
