package co.com.bancounion.inventario.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.bancounion.inventario.model.Category;

public interface ICategoryRepository extends JpaRepository<Category, UUID>{

}
