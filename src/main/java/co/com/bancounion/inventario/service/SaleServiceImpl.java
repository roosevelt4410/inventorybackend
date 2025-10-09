package co.com.bancounion.inventario.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import co.com.bancounion.inventario.model.Product;
import co.com.bancounion.inventario.model.Sale;
import co.com.bancounion.inventario.model.SaleItem;
import co.com.bancounion.inventario.repository.IProductRepository;
import co.com.bancounion.inventario.repository.ISaleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class SaleServiceImpl implements ISaleService{

	 private final ISaleRepository saleRepository;
	    private final IProductRepository productRepository;

	    public SaleServiceImpl(ISaleRepository saleRepository, IProductRepository productRepository) {
	        this.saleRepository = saleRepository;
	        this.productRepository = productRepository;
	    }

	    @Override
	    public List<Sale> findAll() {
	        return saleRepository.findAll();
	    }

	    @Override
	    public Optional<Sale> findById(UUID id) {
	        return saleRepository.findById(id);
	    }
	    @Override
	    public Sale create(Sale sale) {
	    	 BigDecimal total = BigDecimal.ZERO;

	    	    if (sale.getItems() == null || sale.getItems().isEmpty()) {
	    	        throw new IllegalArgumentException("La venta debe tener al menos un item");
	    	    }

	    	    for (SaleItem item : sale.getItems()) {
	    	        Product product = productRepository.findById(item.getProduct().getId())
	    	                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado en venta"));

	    	        if (product.getStock() < item.getQuantity()) {
	    	            throw new IllegalStateException("Stock insuficiente para el producto: " + product.getName());
	    	        }

	    	        // Asegurarnos de que el producto tiene nombre
	    	        if (product.getName() == null || product.getName().isEmpty()) {
	    	            throw new IllegalStateException("El producto tiene un nombre vacío o nulo");
	    	        }

	    	        // Actualizar stock del producto existente
	    	        product.setStock(product.getStock() - item.getQuantity());
	    	        productRepository.save(product);

	    	        // Calcular precios
	    	        item.setUnitPrice(product.getPrice());
	    	        item.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
	    	        item.setPrice(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

	    	        // Asignar la venta y producto existente
	    	        item.setSale(sale);
	    	        item.setProduct(product);

	    	        total = total.add(item.getSubtotal());
	    	    }

	    	    sale.setTotal(total);

	    	    return saleRepository.save(sale); // Aquí se
	    }


	    @Override
	    public void delete(UUID id) {
	        if (!saleRepository.existsById(id)) {
	            throw new EntityNotFoundException("Venta no encontrada con id: " + id);
	        }
	        saleRepository.deleteById(id);
	    }

}
