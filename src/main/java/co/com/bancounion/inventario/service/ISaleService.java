package co.com.bancounion.inventario.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import co.com.bancounion.inventario.model.Sale;

public interface ISaleService {
	List<Sale> findAll();
	 Optional<Sale> findById(UUID id);
    Sale create(Sale sale);
    void delete(UUID id);
}
