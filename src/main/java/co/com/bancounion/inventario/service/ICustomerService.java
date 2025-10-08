package co.com.bancounion.inventario.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import co.com.bancounion.inventario.model.Customer;

public interface ICustomerService {
	 List<Customer> findAll();
	 Optional<Customer> findById(UUID id);
	    Customer create(Customer customer);
	    Customer update(UUID id, Customer customer);
	    void delete(UUID id);
}
