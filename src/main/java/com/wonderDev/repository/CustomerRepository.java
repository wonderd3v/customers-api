package com.wonderDev.repository;

import com.wonderDev.model.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {
    public Customer findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public List<Customer> findPaginated(int page, int size) {
        return findAll().page(Page.of(page, size)).list();
    }

    public long countCustomers() {
        return count();
    }
}
