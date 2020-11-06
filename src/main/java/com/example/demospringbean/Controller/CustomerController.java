package com.example.demospringbean.Controller;

import com.example.demospringbean.Model.Customer;
import com.example.demospringbean.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers/getall")
    public Iterable<Customer> getAll(){
        return customerService.getAll();
    }

    @GetMapping("/customers/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        customerService.deleteCustomder(id);
    }

    @PostMapping("/customers/create")
    public Customer createCustomer(@RequestBody Customer getCustomer){
        Customer postCustomer = new Customer();
        postCustomer.setName(getCustomer.getName());
        postCustomer.setAge(getCustomer.getAge());
        postCustomer.setEmail(getCustomer.getEmail());
        return customerService.createCustomer(postCustomer);
    }
    @GetMapping("/customers/findById/{id}")
    public Optional<Customer> findById(@PathVariable("id") Integer id){
        return customerService.findById(id);
    }
}
