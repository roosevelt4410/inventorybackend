package co.com.bancounion.inventario.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import co.com.bancounion.inventario.model.Product;
import co.com.bancounion.inventario.repository.IProductRepository;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class ProductServiceImpl implements IProductService{

	 private final IProductRepository repository;

	    public ProductServiceImpl(IProductRepository repository) {
	        this.repository = repository;
	    }

	    @Override
	    public List<Product> findAll() {
	        return repository.findAll();
	    }

	    @Override
	    public Optional<Product> findById(UUID id) {
	        return repository.findById(id);
	    }

	    @Override
	    public Product save(Product product) {
	        return repository.save(product);
	    }

	    @Override
	    public void deleteById(UUID id) {
	        repository.deleteById(id);
	    }

}
