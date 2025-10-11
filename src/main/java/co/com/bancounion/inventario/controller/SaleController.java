package co.com.bancounion.inventario.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.bancounion.inventario.model.Sale;
import co.com.bancounion.inventario.service.ISaleService;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "*")
public class SaleController {
	
	private final ISaleService saleService;

    public SaleController(ISaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public List<Sale> getAll() {
        return saleService.findAll();
    }

    @GetMapping("/{id}")
    public Sale getById(@PathVariable UUID id) {
        return saleService.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale no encontrado"));
    }


    @PostMapping
    public Sale create(@RequestBody Sale sale) {
        return saleService.create(sale);
    }
    
    @PutMapping("/{id}")
    public Sale updateSale(@PathVariable UUID id, @RequestBody Sale updatedSale) {
        Sale existing = saleService.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        // 🔹 Actualizar campos básicos
        existing.setCustomer(updatedSale.getCustomer());
        existing.setPaymentMethod(updatedSale.getPaymentMethod());
        existing.setTotal(updatedSale.getTotal());
        existing.setSaleDate(updatedSale.getSaleDate());

        // 🔹 Limpiar los ítems actuales y reemplazarlos por los nuevos
        existing.getItems().clear();

        if (updatedSale.getItems() != null) {
            updatedSale.getItems().forEach(item -> {
                item.setSale(existing); // importante para mantener la relación
                existing.getItems().add(item);
            });
        }

        // 🔹 Guardar y devolver
        return saleService.create(existing);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
    	saleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
