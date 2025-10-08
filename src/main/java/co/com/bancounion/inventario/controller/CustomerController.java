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

import co.com.bancounion.inventario.model.Customer;
import co.com.bancounion.inventario.service.ICustomerService;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class CustomerController {
	
	private final ICustomerService clientService;

    public CustomerController(ICustomerService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Customer> getAll() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable UUID id) {
        return clientService.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @PostMapping
    public Customer create(@RequestBody Customer client) {
        return clientService.create(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable UUID id, @RequestBody Customer client) {
        return ResponseEntity.ok(clientService.update(id, client));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
