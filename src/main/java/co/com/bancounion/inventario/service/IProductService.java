package co.com.bancounion.inventario.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import co.com.bancounion.inventario.model.Product;

public interface IProductService {
	
	List<Product> findAll();
    Optional<Product> findById(UUID id);
    Product save(Product product);
    void deleteById(UUID id);

}
