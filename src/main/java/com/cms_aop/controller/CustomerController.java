package com.cms_aop.controller;

import com.cms_aop.model.Address;
import com.cms_aop.model.Customer;
import com.cms_aop.service.GeneralService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping({"/", "/customers"})
public class CustomerController {
    private final GeneralService<Customer> customerService;

    public CustomerController(GeneralService<Customer> customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ModelAndView listCustomers() {
        ModelAndView modelAndView = new ModelAndView("list");
        List<Customer> customers = customerService.findAll();
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("customer", customerService.findById(id));
        modelAndView.addObject("address", new Address());
        return modelAndView;
    }

    @PostMapping("/create")
    public String saveCustomer(@ModelAttribute Customer customer) {
        customerService.save(customer);
//        ModelAndView modelAndView = new ModelAndView("create");
//        modelAndView.addObject("customer", new Customer());
//        modelAndView.addObject("message", "New customer created successfully");
        return "redirect:/customers";
    }
    @PostMapping("/add-address/{id}")
    public ModelAndView addAddress(@ModelAttribute(name = "address") Address address, @PathVariable("id") Long id) {
        Customer customer = customerService.findById(id);
        address.setId(null);
        address.setCustomer(customer);
        customer.getAddresses().add(address);
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("address", new Address());
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id){
        customerService.remove(id);
        return "redirect:/customers";
    }
}
