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
	            // Buscar el producto
	            Product product = productRepository.findById(item.getProductId())
	                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

	            // Validar stock
	            if (product.getStock() < item.getQuantity()) {
	                throw new IllegalStateException("Stock insuficiente para el producto: " + product.getName());
	            }

	            // Descontar stock
	            product.setStock(product.getStock() - item.getQuantity());
	            productRepository.save(product);

	            // Copiar información del producto al item
	            item.setProductId(product.getId());
	            item.setProductName(product.getName());
	            item.setUnitPrice(product.getPrice());
	            item.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
	            item.setSale(sale);

	            total = total.add(item.getSubtotal());
	        }

	        sale.setTotal(total);
	        return saleRepository.save(sale);
	    }
	    
	    @Override
	    public Sale update(UUID id, Sale updatedSale) {
	        Sale existing = saleRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Venta no encontrada con id: " + id));

	        // 🔹 Actualizar datos principales
	        existing.setCustomer(updatedSale.getCustomer());
	        existing.setPaymentMethod(updatedSale.getPaymentMethod());
	        existing.setTotal(updatedSale.getTotal());
	        existing.setSaleDate(updatedSale.getSaleDate());

	        // 🔹 Limpiar los ítems antiguos y reemplazarlos por los nuevos
	        existing.getItems().clear();

	        if (updatedSale.getItems() != null) {
	            updatedSale.getItems().forEach(item -> {
	                item.setSale(existing); // Mantiene la relación bidireccional
	                existing.getItems().add(item);
	            });
	        }

	        // 🔹 Guardar y devolver
	        return saleRepository.save(existing);
	    }




	    @Override
	    public void delete(UUID id) {
	        if (!saleRepository.existsById(id)) {
	            throw new EntityNotFoundException("Venta no encontrada con id: " + id);
	        }
	        saleRepository.deleteById(id);
	    }

}
