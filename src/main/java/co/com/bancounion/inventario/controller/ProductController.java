package co.com.bancounion.inventario.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.bancounion.inventario.model.Category;
import co.com.bancounion.inventario.model.Product;
import co.com.bancounion.inventario.service.ICategoryService;
import co.com.bancounion.inventario.service.IProductService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

	
	private final IProductService service;
	private final ICategoryService serviceCategori;

    public ProductController(IProductService service, ICategoryService serviceCategori) {
        this.service = service;
        this.serviceCategori = serviceCategori;
    }

    @GetMapping
    public List<Product> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable UUID id) {
        return service.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Category cat = serviceCategori.findById(product.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            product.setCategory(cat);
        }
        return service.save(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable UUID id, @RequestBody Product updated) {
        Product existing = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        existing.setName(updated.getName());
        existing.setSku(updated.getSku());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());
        existing.setMinStock(updated.getMinStock());
        existing.setCategory(updated.getCategory());
        return service.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.deleteById(id);
    }
}
