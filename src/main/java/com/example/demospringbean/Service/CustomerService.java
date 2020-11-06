package com.example.demospringbean.Service;

import com.example.demospringbean.Model.Customer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

@Service
public interface CustomerService {
 public Iterable<Customer> getAll();
 public Customer createCustomer(Customer customer);
 public void deleteCustomder(Integer id);
 public Optional<Customer> findById(Integer id);
}
