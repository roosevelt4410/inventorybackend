package co.com.bancounion.inventario.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import co.com.bancounion.inventario.model.Category;

public interface ICategoryService {
	List<Category> findAll();
	Optional<Category> findById(UUID id);
    Category update(UUID id, Category category);
    Category save(Category category);
    void delete(UUID id);
}
